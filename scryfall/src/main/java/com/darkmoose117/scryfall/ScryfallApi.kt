package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.api.ScryfallCardsApi
import com.darkmoose117.scryfall.api.ScryfallSetsApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ScryfallApi {

    private val moshiConverterFactory by lazy {
        MoshiConverterFactory.create(
            Moshi.Builder().customizeForScryfall().build()
        )
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = Level.HEADERS
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.scryfall.com")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    val setsApi: ScryfallSetsApi by lazy { retrofit.create(ScryfallSetsApi::class.java) }

    val cardsApi: ScryfallCardsApi by lazy { retrofit.create(ScryfallCardsApi::class.java) }
}
