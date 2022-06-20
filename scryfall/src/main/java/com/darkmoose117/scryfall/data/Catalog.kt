package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Catalog(
    /** A link to the current catalog on Scryfall’s API. */
    @Json(name = "uri") val uri: String = "",
    /** The number of items in the data array. */
    @Json(name = "total_values") val total_values: Int = 0,
    /** An array of datapoints, as strings. */
    @Json(name = "data") val data: List<String> = emptyList(),
) : ScryfallObject(ObjectType.CatalogObject)