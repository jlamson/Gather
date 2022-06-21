package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class Color {
    @Json(name = "W") White,
    @Json(name = "U") Blue,
    @Json(name = "B") Black,
    @Json(name = "R") Red,
    @Json(name = "G") Green,
    @Json(name = "C") Colorless,
    @Json(name = "?") NotHandled
}