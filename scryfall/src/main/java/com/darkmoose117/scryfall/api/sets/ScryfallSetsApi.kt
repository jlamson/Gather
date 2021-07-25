package com.darkmoose117.scryfall.api.sets

import com.darkmoose117.scryfall.data.DataList
import com.darkmoose117.scryfall.data.MagicSet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScryfallSetsApi {

    @GET("sets")
    suspend fun getAllSets(
        @Query("pretty") pretty: Boolean? = null
    ): Response<DataList<MagicSet>>

    @GET("sets/{setCode}")
    suspend fun getSetByCode(
        @Path("setCode") setCode: String,
        @Query("pretty") pretty: Boolean? = null
    ): Response<MagicSet>

    @GET("sets/tcgplayer/{id}")
    suspend fun getSetByTcgPlayerId(
        @Path("id") id: String,
        @Query("pretty") pretty: Boolean? = null
    ): Response<MagicSet>

    @GET("sets/{id}")
    suspend fun getSetById(
        @Path("id") id: String,
        @Query("pretty") pretty: Boolean? = null
    ): Response<MagicSet>
}