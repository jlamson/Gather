package com.darkmoose117.gather.data.cards

import com.darkmoose117.scryfall.api.cards.ScryfallCardsApi
import com.darkmoose117.scryfall.api.params.OrderString
import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.DataList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException

class CardRepository(
    private val api: ScryfallCardsApi,
) {

    private val apiMutex = Mutex()

    private var lastQuery: String? = null
    set(value) {
        if (field != value) {
            cardIdMap.clear()
            field = value
        }
    }
    private val cardIdMap: MutableMap<String, Card> = mutableMapOf()

    suspend fun getCardsBySearch(
        query: String,
        @OrderString order: String? = null,
        page: Int? = null,
    ) : Result<DataList<Card>> = apiMutex.withLock {
        val response = api.getCardsBySearch(
            query = query,
            order = order,
            page = page
        )

        return if (response.isSuccessful) {
            lastQuery = query
            response.body()?.let { safeBody ->
                safeBody.data.forEach { cardIdMap[it.id] = it }
                Result.success(safeBody)
            } ?: Result.failure(NoCardsFoundError(query))
        } else {
            Result.failure(HttpException(response))
        }
    }

    suspend fun getCardsById(
        id: String,
        forceReload: Boolean = false
    ): Result<Card> = apiMutex.withLock {
        if (!forceReload && cardIdMap.containsKey(id)) {
            return Result.success(cardIdMap[id]!!)
        }

        val response = api.getCardById(id)
        return if (response.isSuccessful) {
            response.body()?.let { safeBody ->
                Result.success(safeBody)
            } ?: Result.failure(HttpException(response))
        } else {
            Result.failure(HttpException(response))
        }
    }
}