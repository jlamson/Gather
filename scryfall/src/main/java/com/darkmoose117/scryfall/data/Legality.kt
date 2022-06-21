package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class Legality {
    @Json(name = "legal") Legal,
    @Json(name = "not_legal") NotLegal,
    @Json(name = "restricted") Restricted,
    @Json(name = "banned") Banned,
    @Json(name = "not_handled") NotHandled,
}