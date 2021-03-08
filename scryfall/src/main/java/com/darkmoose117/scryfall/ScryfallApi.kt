package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.api.ScryfallSetsApi
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ScryfallApi {

    private val moshiConverterFactory by lazy {
        MoshiConverterFactory.create(
            Moshi.Builder().customizeForScryfall().build()
        )
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.scryfall.com")
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    val setsApi: ScryfallSetsApi by lazy { retrofit.create(ScryfallSetsApi::class.java) }

}