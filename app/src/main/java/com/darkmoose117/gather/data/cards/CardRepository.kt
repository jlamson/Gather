package com.darkmoose117.gather.data.cards

import com.darkmoose117.gather.util.Result
import com.darkmoose117.scryfall.api.cards.ScryfallCardsApi
import com.darkmoose117.scryfall.api.params.OrderString
import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.DataList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class CardRepository @Inject constructor(
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
                Result.Success(safeBody)
            } ?: Result.Failure(NoCardsFoundError(query))
        } else {
            Result.Failure(HttpException(response))
        }
    }

    suspend fun getCardById(
        id: String,
        forceReload: Boolean = false
    ): Result<Card> = apiMutex.withLock {
        if (!forceReload && cardIdMap.containsKey(id)) {
            return Result.Success(cardIdMap[id]!!)
        }

        val response = api.getCardById(id)
        return if (response.isSuccessful) {
            response.body()?.let { safeBody ->
                Timber.d("safeBody: $safeBody")
                Result.Success(safeBody)
            } ?: Result.Failure(HttpException(response))
        } else {
            Result.Failure(HttpException(response))
        }
    }
}