package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/** [DOCS](https://scryfall.com/docs/api/sets) A Set object represents a group of related Magic cards. */
@JsonClass(generateAdapter = true)
data class MagicSet(
    /** A unique ID for this set on Scryfall that will not change. */
    @Json(name = "id") val id: String = "",
    /** The unique three to five-letter code for this set. */
    @Json(name = "code") val code: String = "",
    /** The unique code for this set on MTGO, which may differ from the regular code. */
    @Json(name = "mtgo_code") val mtgoCode: String? = null,
    /** This set’s ID on TCGplayer’s API, also known as the groupId. */
    @Json(name = "tcgplayer_id") val tcgplayerId: Int? = null,
    /** The English name of the set. */
    @Json(name = "name") val name: String = "",
    /** A computer-readable classification for this set. See below. */
    @Json(name = "set_type") val setType: String = "",
    /** The date the set was released or the first card was printed in the set (in GMT-8 Pacific time). */
    @Json(name = "released_at") val releasedAt: String? = null,
    /** The block code for this set, if any. */
    @Json(name = "block_code") val blockCode: String? = null,
    /** The block or group name code for this set, if any. */
    @Json(name = "block") val block: String? = null,
    /** The set code for the parent set, if any. promo and token sets often have a parent set. */
    @Json(name = "parent_set_code") val parentSetCode: String? = null,
    /** The number of cards in this set. */
    @Json(name = "card_count") val cardCount: Int = 0,
    /** The denominator for the set’s printed collector numbers. */
    @Json(name = "printed_size") val printedSize: Int? = null,
    /** True if this set was only released in a video game. */
    @Json(name = "digital") val digital: Boolean = false,
    /** True if this set contains only foil cards. */
    @Json(name = "foil_only") val foilOnly: Boolean = false,
    /** True if this set contains only nonfoil cards. */
    @Json(name = "nonfoil_only") val nonfoilOnly: Boolean = false,
    /** A link to this set’s permapage on Scryfall’s website. */
    @Json(name = "scryfall_uri") val scryfallUri: String = "",
    /** A link to this set object on Scryfall’s API. */
    @Json(name = "uri") val uri: String = "",
    /** A URI to an SVG file for this set’s icon on Scryfall’s CDN. Hotlinking this image isn’t recommended, because it may change slightly over time. You should download it and use it locally for your particular user interface needs. */
    @Json(name = "icon_svg_uri") val iconSvgUri: String = "",
    /** A Scryfall API URI that you can request to begin paginating over the cards in this set. */
    @Json(name = "search_uri") val searchUri: String = "",
) : ScryfallObject(ObjectType.SetObject)