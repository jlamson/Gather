package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class Games {
    @Json(name = "paper") Paper,
    @Json(name = "arena") Arena,
    @Json(name = "mtgo") Mtgo,
    @Json(name = "NotHandled") NotHandled,
}