package com.darkmoose117.gather.di

import com.darkmoose117.gather.data.android.AndroidRawResourceRepository
import com.darkmoose117.gather.data.android.RawResourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourcesModule {

    @Binds
    abstract fun bindRawResourceRepository(
        impl: AndroidRawResourceRepository
    ): RawResourceRepository
}