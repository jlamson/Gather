package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class Frame {
    /** The original Magic card frame, starting from Limited Edition Alpha. */
    @Json(name = "1993") Original,
    /** The updated classic frame starting from Mirage block */
    @Json(name = "1997") Classic,
    /** The “modern” Magic card frame, introduced in Eighth Edition and Mirrodin block. */
    @Json(name = "2003") Modern,
    /** The holofoil-stamp Magic card frame, introduced in Magic 2015. */
    @Json(name = "2015") Holofoil,
    /** The frame used on cards from the future */
    @Json(name = "future") Future,
}