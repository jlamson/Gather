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
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.set.MtgSet
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

    private var sortedBy = SortedBy.Name
    private var loadedSets: List<MtgSet>? = null
    private var typeFilters: MutableMap<String, Boolean> = mutableMapOf()
    private var typeCount: Map<String, Int> = mapOf()

    fun loadSets() {
        _viewState.postValue(Loading)
        viewModelScope.launch(contextProvider.IO) {
            val response = MtgSetApiClient.getAllSets()
            if (response.isSuccessful) {
                val sets = response.body()
                if (sets != null) {
                    loadedSets = sets
                    typeCount = sets.groupingBy { it.type }.eachCount()
                    typeFilters = sets.map { it.type }.toSet().associateWith { true }.toMutableMap()

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
            SortedBy.Name -> SortedBy.Date
            SortedBy.Date -> SortedBy.Name
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

    private fun List<MtgSet>.sortedWith(sort: SortedBy): List<MtgSet> = when (sort) {
        SortedBy.Name -> this.sortedBy { it.name }
        SortedBy.Date -> this.sortedByDescending { it.releaseDate.millis }
    }.filter {
        typeFilters[it.type] == true
    }

    private fun buildTypeDescriptors(): List<TypeDescriptor> = typeFilters.keys
        .sortedByDescending { typeCount[it] }
        .map { TypeDescriptor(it, typeCount[it]!!, typeFilters[it]!!) }
}

@Immutable
sealed class SetsViewState {
    object Loading : SetsViewState()
    data class Success(
        val sets: List<MtgSet>,
        val typeDescriptors: List<TypeDescriptor>,
        val sortBy: SortedBy
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
enum class SortedBy {
    Name, Date
}
