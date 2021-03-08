package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
enum class Rarity {
    @Json(name = "common") Common,
    @Json(name = "uncommon") Uncommon,
    @Json(name = "rare") Rare,
    @Json(name = "special") Special,
    @Json(name = "mythic") Mythic,
    @Json(name = "bonus") Bonus,
}