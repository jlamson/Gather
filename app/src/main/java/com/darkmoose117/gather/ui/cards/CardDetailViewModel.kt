package com.darkmoose117.gather.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.data.cards.CardRepository
import com.darkmoose117.gather.ui.sets.SetsViewState
import com.darkmoose117.gather.util.CoroutineContextProvider
import com.darkmoose117.gather.util.Result
import com.darkmoose117.scryfall.data.Card
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class CardDetailViewModel(
    private val id: String,
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

    init {
        getCard()
    }

    private fun getCard() {
        viewModelScope.launch(contextProvider.IO) {
            val state = try {
                when (val result = repository.getCardById(id)) {
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