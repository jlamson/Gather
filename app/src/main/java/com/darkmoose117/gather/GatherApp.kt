package com.darkmoose117.gather

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import coil.util.DebugLogger
import okhttp3.OkHttpClient
import timber.log.Timber

class GatherApp: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(applicationContext)
        .crossfade(true)
        .logger(DebugLogger())
        .okHttpClient {
            OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(applicationContext))
                .build()
        }
        .build()
}