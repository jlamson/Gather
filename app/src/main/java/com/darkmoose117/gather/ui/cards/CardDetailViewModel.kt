package com.darkmoose117.gather.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.data.cards.CardRepository
import com.darkmoose117.gather.ui.nav.Nav
import com.darkmoose117.gather.util.CoroutineContextProvider
import com.darkmoose117.gather.util.MissingArgException
import com.darkmoose117.gather.util.Result
import com.darkmoose117.scryfall.data.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CardRepository
) : ViewModel() {

    private val _viewState: MutableLiveData<CardDetailViewState> by lazy {
        MutableLiveData(CardDetailViewState.Loading)
    }
    val viewState: LiveData<CardDetailViewState> = Transformations.distinctUntilChanged(_viewState)

    private val contextProvider = CoroutineContextProvider(handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _viewState.postValue(CardDetailViewState.Failure(throwable))
    })

    private val id: String get() = savedStateHandle[Nav.Args.ID]
        ?: throw MissingArgException(Nav.Args.ID)

    init {
        getCard(forceReload = false)
    }

    fun refreshCard() = getCard(forceReload = true)

    private fun getCard(forceReload: Boolean = false) {
        viewModelScope.launch(contextProvider.IO) {
            val state = try {
                when (val result = repository.getCardById(id, forceReload = forceReload)) {
                    is Result.Success -> CardDetailViewState.Success(result.value)
                    is Result.Failure -> CardDetailViewState.Failure(result.exception)
                }
            } catch (e: Exception) {
                CardDetailViewState.Failure(e)
            }

            _viewState.postValue(state)
        }
    }

}

sealed class CardDetailViewState {
    object Loading: CardDetailViewState()
    data class Success(
        val card: Card
    ) : CardDetailViewState()
    class Failure(
        val throwable: Throwable
    ) : CardDetailViewState()
}