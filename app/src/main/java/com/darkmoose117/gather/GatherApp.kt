package com.darkmoose117.gather

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.util.CoilUtils
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber

@HiltAndroidApp
class GatherApp: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(applicationContext)
        .components {
            add(SvgDecoder.Factory(true))
        }
        .crossfade(true)
        .logger(DebugLogger())
        .okHttpClient {
            OkHttpClient.Builder().build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(this@GatherApp.cacheDir.resolve("image_cache"))
                .build()
        }
        .build()
}