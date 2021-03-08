package com.darkmoose117.scryfall.api

import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.DataList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallCardsApi {

    @GET("cards/search")
    suspend fun getCardsBySearch(
        @Query("q") query: String,
        @Query("unique") unique: String? = null,
        @Query("order") order: String? = null,
        @Query("dir") dir: String? = null,
        @Query("page") page: Int? = null,
        @Query("pretty") pretty: Boolean? = null
    ) : Response<DataList<Card>>

}