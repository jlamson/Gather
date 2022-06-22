package com.darkmoose117.gather.ui.sets

import androidx.compose.runtime.Immutable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.ui.sets.SetsViewState.Failure
import com.darkmoose117.gather.ui.sets.SetsViewState.Loading
import com.darkmoose117.gather.ui.sets.SetsViewState.Success
import com.darkmoose117.gather.util.CoroutineContextProvider
import com.darkmoose117.scryfall.api.sets.ScryfallSetsApi
import com.darkmoose117.scryfall.data.MagicSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SetsViewModel @Inject constructor(
    private val setsApi: ScryfallSetsApi
) : ViewModel() {

    private var sortedBy = SetsSortedBy.Date
    private var setsByName: List<MagicSetRow>? = null
    private var setsByDate: List<MagicSetRow>? = null
    private var typeFilters: MutableMap<String, Boolean> = mutableMapOf()
    private var typeCount: Map<String, Int> = mapOf()

    private val contextProvider = CoroutineContextProvider(handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _viewState.postValue(Failure(throwable))
    })

    private val _viewState by lazy {
        MutableLiveData<SetsViewState>(Loading).also {
            loadSets()
        }
    }
    val viewState by lazy {
        Transformations.distinctUntilChanged(_viewState)
    }

    private fun loadSets() {
        viewModelScope.launch(contextProvider.IO) {
            val response = setsApi.getAllSets()
            if (response.isSuccessful) {
                val sets = response.body()?.data
                if (!sets.isNullOrEmpty()) {
                    setsByName = sets.sortedBy { it.name }.map { MagicSetRow(0, it) }
                    setsByDate = buildSetsByDate(sets)
                    typeCount = sets.groupingBy { it.setType }.eachCount()
                    typeFilters = sets.map { it.setType }.toSet().associateWith { true }.toMutableMap()

                    updateList()
                } else {
                    _viewState.postValue(Failure(Throwable("No sets returned")))
                }
            } else {
                _viewState.postValue(Failure(Throwable("Fetch to load sets failed: ${response.code()}")))
            }
        }
    }

    fun toggleSort() {
        sortedBy = when (sortedBy) {
            SetsSortedBy.Name -> SetsSortedBy.Date
            SetsSortedBy.Date -> SetsSortedBy.Name
        }

        updateList()
    }

    fun toggleType(type: String) {
        val previous = typeFilters[type]
        if (previous != null) {
            typeFilters[type] = !previous
        }

        updateList()
    }

    fun toggleAllTypes() {
        val allEnabled = typeFilters.all { it.value }
        typeFilters.forEach {
            typeFilters[it.key] = !allEnabled
        }

        updateList()
    }

    private fun updateList() {
        val safeSort = sortedBy
        val safeSets = when (safeSort) {
            SetsSortedBy.Name -> setsByName
            SetsSortedBy.Date -> setsByDate
        } ?: return
        _viewState.postValue(
            Success(
                safeSets.filter { typeFilters[it.set.setType] == true },
                buildTypeDescriptors(),
                safeSort
            )
        )
    }

    private fun buildSetsByDate(sets: List<MagicSet>): List<MagicSetRow> {
        val noParentsSets = mutableListOf<MagicSet>()
        val childrenSets = mutableMapOf<String, MutableList<MagicSet>>()
        sets.forEach { set ->
            val parentSetCode: String? = set.parentSetCode
            if (parentSetCode == null) {
                noParentsSets.add(set)
            } else {
                val childrenList = if (childrenSets.containsKey(parentSetCode)) {
                    childrenSets[parentSetCode]!!
                } else {
                    val newList = mutableListOf<MagicSet>()
                    childrenSets[parentSetCode] = newList
                    newList
                }

                childrenList.add(set)
            }
        }

        noParentsSets.sortByDescending(MagicSet::releasedAt)

        val rows: MutableList<MagicSetRow> = mutableListOf()
        var depth = 0
        fun addSetThenChildren(set: MagicSet) {
            rows.add(MagicSetRow(depth, set))
            depth += 1
            childrenSets[set.code]?.forEach { child ->
                addSetThenChildren(child)
            }
            depth -= 1
        }
        noParentsSets.forEach { set ->
            addSetThenChildren(set)
        }

        return rows.toList()
    }

    private fun buildTypeDescriptors(): List<TypeDescriptor> = typeFilters.keys
        .sortedByDescending { typeCount[it] }
        .map { TypeDescriptor(it, typeCount[it]!!, typeFilters[it]!!) }
}

@Immutable
sealed class SetsViewState {
    object Loading : SetsViewState()
    data class Success(
        val sets: List<MagicSetRow>,
        val typeDescriptors: List<TypeDescriptor>,
        val setsSortedBy: SetsSortedBy
    ) : SetsViewState()
    class Failure(
        val throwable: Throwable
    ) : SetsViewState()
}

data class TypeDescriptor(
    val type: String,
    val count: Int,
    val shouldDisplay: Boolean
)

@Immutable
enum class SetsSortedBy {
    Name, Date
}

data class MagicSetRow(
    val depth: Int,
    val set: MagicSet
)
