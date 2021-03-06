package com.darkmoose117.gather.ui.sets

import androidx.compose.runtime.Immutable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.util.CoroutineContextProvider
import io.magicthegathering.kotlinsdk.api.MtgCardApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.jar.Attributes

class CardsBySetViewModel : ViewModel() {

    private val contextProvider = CoroutineContextProvider(handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _viewState.postValue(CardsBySetViewState.Failure(throwable))
    })

    private val _viewState = MutableLiveData<CardsBySetViewState>(CardsBySetViewState.Loading)
    val viewState = Transformations.distinctUntilChanged(_viewState)

    private var sortedBy = CardsSortedBy.Number
    private var loadedCards: List<MtgCard>? = null

    fun loadCards(setCode: String?) {
        if (setCode == null) {
            _viewState.postValue(CardsBySetViewState.Failure(IllegalArgumentException("SetCode not defined")))
            return
        }

        _viewState.postValue(CardsBySetViewState.Loading)
        viewModelScope.launch(contextProvider.IO) {
            val response = MtgCardApiClient.getAllCardsBySetCode(setCode, pageSize = 1000)
            if (response.isSuccessful) {
                val cards = response.body()
                if (cards != null) {
                    loadedCards = cards

                    updateList()
                } else {
                    _viewState.postValue(CardsBySetViewState.Failure(Throwable("No sets returned")))
                }
            } else {
                _viewState.postValue(CardsBySetViewState.Failure(Throwable("Fetch to load sets failed: ${response.code()}")))
            }
        }
    }

    fun toggleSort() {
        sortedBy = when (sortedBy) {
            CardsSortedBy.Name -> CardsSortedBy.Number
            CardsSortedBy.Number -> CardsSortedBy.Name
        }

        updateList()
    }

    private fun updateList() {
        val safeCards = loadedCards ?: return
        val safeSort = sortedBy
        _viewState.postValue(
            CardsBySetViewState.Success(
                safeCards.sortedWith(safeSort),
                safeSort
            )
        )
    }

    private fun List<MtgCard>.sortedWith(sort: CardsSortedBy): List<MtgCard> = when (sort) {
        CardsSortedBy.Number -> this.sortedBy { it.number!!.toInt() }
        CardsSortedBy.Name -> this.sortedBy { it.name }
    }
}

@Immutable
sealed class CardsBySetViewState {
    object Loading : CardsBySetViewState()
    data class Success(
        val cards: List<MtgCard>,
        val cardsSortedBy: CardsSortedBy
    ) : CardsBySetViewState()
    class Failure(
        val throwable: Throwable
    ) : CardsBySetViewState()
}

@Immutable
enum class CardsSortedBy {
    Number, Name
}
