package com.darkmoose117.gather.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class CoroutineContextProvider(
    initialMain: CoroutineContext = Dispatchers.Main,
    initialDefault: CoroutineContext = Dispatchers.Default,
    initialIo: CoroutineContext = Dispatchers.IO,
    handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag(CoroutineContextProvider::class.java.simpleName)
            .e(throwable, "Crash in Coroutine")
    }
) {
    val Main = initialMain + handler
    val Default = initialDefault + handler
    val IO = initialIo + handler
}