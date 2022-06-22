package com.darkmoose117.gather.di

import com.darkmoose117.scryfall.api.cards.ScryfallCardsApi
import com.darkmoose117.scryfall.api.sets.ScryfallSetsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideSetsApi(retrofit: Retrofit): ScryfallSetsApi = retrofit
        .create(ScryfallSetsApi::class.java)

    @Provides
    fun provideCardsApi(retrofit: Retrofit): ScryfallCardsApi = retrofit
        .create(ScryfallCardsApi::class.java)
}