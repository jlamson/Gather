package com.darkmoose117.gather.data.cards

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.darkmoose117.scryfall.api.cards.ScryfallCardsApi
import com.darkmoose117.scryfall.api.params.OrderString
import com.darkmoose117.scryfall.data.Card

class PagedCardSource(
    private val api: ScryfallCardsApi,
    private val query: String,
    @OrderString
    private var order: String? = null
) : PagingSource<Int, Card>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = api.getCardsBySearch(
                query = query,
                order = order,
                page = nextPageNumber
            )
            val body = response.body()
            val cardList = body?.data ?: emptyList()
            val nextKeyMaybe = if (!body?.nextPage.isNullOrBlank()) {
                nextPageNumber + 1
            } else null

            return LoadResult.Page(
                data = cardList,
                prevKey = null, // Only paging forward.
                nextKey = nextKeyMaybe
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}