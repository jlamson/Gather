package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
enum class BorderColor {
    @Json(name = "black") Black,
    @Json(name = "borderless") Borderless,
    @Json(name = "gold") Gold,
    @Json(name = "silver") Silver,
    @Json(name = "white") White,
}