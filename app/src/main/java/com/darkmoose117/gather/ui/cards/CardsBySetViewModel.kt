package com.darkmoose117.gather.ui.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmoose117.gather.util.CoroutineContextProvider
import com.darkmoose117.scryfall.ScryfallApi
import com.darkmoose117.scryfall.api.params.Order
import com.darkmoose117.scryfall.data.Card
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class CardsBySetViewModel : ViewModel() {

    private val contextProvider = CoroutineContextProvider(handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _viewState.postValue(CardListViewState.Failure(throwable))
    })

    private val _viewState = MutableLiveData<CardListViewState>(CardListViewState.Loading)
    val viewState = Transformations.distinctUntilChanged(_viewState)

    private val cardsApi = ScryfallApi.cardsApi

    private var sortedBy = CardsSortedBy.Number
    private var cardsViewType = CardsViewType.Image
    private var loadedCards: List<Card>? = null

    fun loadCards(setCode: String?) {
        if (setCode == null) {
            _viewState.postValue(CardListViewState.Failure(IllegalArgumentException("SetCode not defined")))
            return
        }

        _viewState.postValue(CardListViewState.Loading)
        viewModelScope.launch(contextProvider.IO) {
            val response = cardsApi.getCardsBySearch(query = "e:$setCode", order = Order.SET, pretty = true)
            if (response.isSuccessful) {
                val cards = response.body()?.data
                if (!cards.isNullOrEmpty()) {
                    loadedCards = cards

                    updateList()
                } else {
                    _viewState.postValue(CardListViewState.Failure(Throwable("No cards returned")))
                }
            } else {
                _viewState.postValue(CardListViewState.Failure(Throwable("Fetch to load sets failed: ${response.code()}")))
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

    fun toggleViewType() {
        cardsViewType = when (cardsViewType) {
            CardsViewType.Image -> CardsViewType.Text
            CardsViewType.Text -> CardsViewType.Image
        }

        updateList()
    }

    private fun updateList() {
        val safeCards = loadedCards ?: return
        val safeSort = sortedBy
        _viewState.postValue(
            CardListViewState.Success(
                safeCards.sortedWith(safeSort),
                safeSort,
                cardsViewType
            )
        )
    }

    private fun List<Card>.sortedWith(sort: CardsSortedBy): List<Card> = when (sort) {
        CardsSortedBy.Number -> this.sortedBy { it.collectorNumber.toInt() }
        CardsSortedBy.Name -> this.sortedBy { it.name }
    }
}
