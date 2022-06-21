package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class Component {
    @Json(name = "token") Token,
    @Json(name = "meld_part") MeldPart,
    @Json(name = "meld_result") MeldResult,
    @Json(name = "combo_piece") ComboPiece,
    @Json(name = "not_handled") NotHandled,
}