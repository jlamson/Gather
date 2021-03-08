package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Prices(
    @Json(name = "usd") val usd: String? = null,
    @Json(name = "usd_foil") val usdFoil: String? = null,
    @Json(name = "eur") val eur: String? = null,
    @Json(name = "eur_foil") val eurFoil: String? = null,
    @Json(name = "tix") val tix: String? = null,
)
