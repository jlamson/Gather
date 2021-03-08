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
import com.darkmoose117.scryfall.ScryfallApi
import com.darkmoose117.scryfall.api.ScryfallSetsApi
import com.darkmoose117.scryfall.data.MagicSet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class SetsViewModel : ViewModel() {

    private val contextProvider = CoroutineContextProvider(handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _viewState.postValue(Failure(throwable))
    })

    private val _viewState = MutableLiveData<SetsViewState>(Loading)
    val viewState = Transformations.distinctUntilChanged(_viewState)

    private var sortedBy = SetsSortedBy.Date
    private var loadedSets: List<MagicSet>? = null
    private var typeFilters: MutableMap<String, Boolean> = mutableMapOf()
    private var typeCount: Map<String, Int> = mapOf()

    // TODO INJECT
    private val setsApi: ScryfallSetsApi = ScryfallApi.setsApi

    fun loadSets() {
        _viewState.postValue(Loading)
        viewModelScope.launch(contextProvider.IO) {
            val response = setsApi.getAllSets()
            if (response.isSuccessful) {
                val sets = response.body()?.data
                if (!sets.isNullOrEmpty()) {
                    loadedSets = sets
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
        val safeSets = loadedSets ?: return
        val safeSort = sortedBy
        _viewState.postValue(
            Success(
                safeSets.sortedWith(safeSort),
                buildTypeDescriptors(),
                safeSort
            )
        )
    }

    private fun List<MagicSet>.sortedWith(sort: SetsSortedBy): List<MagicSet> = when (sort) {
        SetsSortedBy.Name -> this.sortedBy { it.name }
        SetsSortedBy.Date -> this.sortedByDescending { it.releasedAt }
    }.filter {
        typeFilters[it.setType] == true
    }

    private fun buildTypeDescriptors(): List<TypeDescriptor> = typeFilters.keys
        .sortedByDescending { typeCount[it] }
        .map { TypeDescriptor(it, typeCount[it]!!, typeFilters[it]!!) }
}

@Immutable
sealed class SetsViewState {
    object Loading : SetsViewState()
    data class Success(
        val sets: List<MagicSet>,
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
