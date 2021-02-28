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

    fun loadSets() {
        _viewState.postValue(Loading)
        viewModelScope.launch(contextProvider.IO) {
            val response = MtgSetApiClient.getAllSets()
            if (response.isSuccessful) {
                val sets = response.body()
                if (sets != null) {
                    _viewState.postValue(Success(sets))
                } else {
                    _viewState.postValue(Failure(Throwable("No sets returned")))
                }
            } else {
                _viewState.postValue(Failure(Throwable("Fetch to load sets failed: ${response.code()}")))
            }
        }
    }
}

@Immutable
sealed class SetsViewState {
    object Loading : SetsViewState()
    data class Success(
        val sets: List<MtgSet>
    ) : SetsViewState()
    class Failure(
        val throwable: Throwable
    ) : SetsViewState()
}