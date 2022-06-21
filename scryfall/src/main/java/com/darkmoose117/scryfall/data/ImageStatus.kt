package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class ImageStatus {
    @Json(name = "missing") Missing,
    @Json(name = "placeholder") Placeholder,
    @Json(name = "lowres") LowRes,
    @Json(name = "highres_scan") HighResScan,
    @Json(name = "not_handled") NotHandled
}