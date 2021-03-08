package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
enum class Games {
    @Json(name = "paper") Paper,
    @Json(name = "arena") Arena,
    @Json(name = "mtgo") Mtgo,
}