package com.darkmoose117.gather.data.android

import android.content.Context
import androidx.annotation.RawRes
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

interface RawResourceRepository {
    fun openRawResource(@RawRes rawResId: Int): InputStream
}

@Singleton
class AndroidRawResourceRepository @Inject constructor(
    @ApplicationContext val appContext: Context
): RawResourceRepository {
    override fun openRawResource(rawResId: Int): InputStream =
        appContext.resources.openRawResource(rawResId)
}