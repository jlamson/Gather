package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
enum class Legality {
    @Json(name = "legal") Legal,
    @Json(name = "not_legal") NotLegal,
    @Json(name = "restricted") Restricted,
    @Json(name = "banned") Banned,
}