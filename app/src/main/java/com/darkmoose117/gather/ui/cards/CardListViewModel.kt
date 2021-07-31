package com.darkmoose117.gather.ui.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.darkmoose117.gather.data.cards.PagedCardSource
import com.darkmoose117.gather.util.CoroutineContextProvider
import com.darkmoose117.scryfall.ScryfallApi
import com.darkmoose117.scryfall.api.cards.ScryfallCardsApi
import com.darkmoose117.scryfall.api.params.Order
import com.darkmoose117.scryfall.data.Card
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

class CardListViewModel(
    private val query: String
) : ViewModel() {

    private var sortedBy = CardsSortedBy.Number
    private var cardsViewType = CardsViewType.Image

    private val _viewState by lazy { MutableLiveData(buildViewState()) }
    val viewState by lazy { Transformations.distinctUntilChanged(_viewState) }

    private lateinit var pagedCardSource: PagedCardSource
    val cardsPager = Pager(
        PagingConfig(
            pageSize = ScryfallCardsApi.PAGE_SIZE,
            prefetchDistance = 50,
            enablePlaceholders = false,
            initialLoadSize = ScryfallCardsApi.PAGE_SIZE,
        )
    ) {
        buildPagerSource()
    }

    fun toggleSort() {
        sortedBy = when (sortedBy) {
            CardsSortedBy.Name -> CardsSortedBy.Number
            CardsSortedBy.Number -> CardsSortedBy.Name
        }

        pagedCardSource.invalidate()
        updateViewState()
    }

    fun toggleViewType() {
        cardsViewType = when (cardsViewType) {
            CardsViewType.Image -> CardsViewType.Text
            CardsViewType.Text -> CardsViewType.Image
        }

        updateViewState()
    }

    fun buildViewState() = CardListViewState(sortedBy, cardsViewType)

    private fun updateViewState() { _viewState.postValue(buildViewState()) }

    private fun buildPagerSource() = PagedCardSource(
        api = ScryfallApi.cardsApi,
        query = query,
        order = sortedBy.order()
    ).also { pagedCardSource = it }
}
