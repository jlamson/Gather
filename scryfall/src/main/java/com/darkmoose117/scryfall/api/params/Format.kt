package com.darkmoose117.scryfall.api.params

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    Format.JSON,
    Format.CSV
)
annotation class FormatString

object Format {
    const val JSON = "json"
    const val CSV = "csv"
}