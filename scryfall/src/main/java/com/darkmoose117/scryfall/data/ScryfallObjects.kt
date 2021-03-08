package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Suppress("UNUSED_PARAMETER")
sealed class ScryfallObject(
    @Json(name = "object") objectType: ObjectType
)

enum class ObjectType(val type: Class<out ScryfallObject>, val jsonName: String) {
    ErrorObject(ScryfallError::class.java, "error"),
    ListObject(DataList::class.java, "list"),
    SetObject(MagicSet::class.java, "set"),
    CardObject(Card::class.java, "card"),
    RelatedCardObject(RelatedCard::class.java, "related_card"),
    CardFaceObject(CardFace::class.java, "card_face"),
    RulingObject(Ruling::class.java, "ruling"),
    CardSymbolObject(CardSymbol::class.java, "card_symbol"),
    CatalogObject(Catalog::class.java, "catalog"),
}

/** [DOCS](https://scryfall.com/docs/api/errors) An Error object represents a failure to find information or understand the input you provided to the API. Error objects are always transmitted with the appropriate `4XX` or `5XX` HTTP status code. */
@JsonClass(generateAdapter = true)
data class ScryfallError(
    /** An integer HTTP status code for this error. */
    @Json(name = "status") val status: Int = 400,
    /** A computer-friendly string representing the appropriate HTTP status code. */
    @Json(name = "code") val code: String = "",
    /** A human-readable string explaining the error. */
    @Json(name = "details") val details: String = "",
    /** A computer-friendly string that provides additional context for the main error. For example, an endpoint many generate HTTP 404 errors for different kinds of input. This field will provide a label for the specific kind of 404 failure, such as ambiguous. */
    @Json(name = "type") val type: String? = null,
    /** If your input also generated non-failure warnings, they will be provided as human-readable strings in this array. */
    @Json(name = "warnings") val warnings: List<String>? = null,
) : ScryfallObject(ObjectType.ErrorObject)

/** [DOCS](https://scryfall.com/docs/api/lists) A List object represents a requested sequence of other objects (Cards, Sets, etc). List objects may be paginated, and also include information about issues raised when generating the list. */
@JsonClass(generateAdapter = true)
data class DataList<T : ScryfallObject>(
    /** An array of the requested objects, in a specific order. */
    @Json(name = "data") val data: List<T> = emptyList(),
    /** True if this List is paginated and there is a page beyond the current page. */
    @Json(name = "has_more") val hasMore: Boolean = false,
    /** If there is a page beyond the current page, this field will contain a full API URI to that page. You may submit a HTTP GET request to that URI to continue paginating forward on this List. */
    @Json(name = "next_page") val nextPage: String? = null,
    /** If this is a list of Card objects, this field will contain the total number of cards found across all pages. */
    @Json(name = "total_cards") val totalCards: Int? = null,
    /** An array of human-readable warnings issued when generating this list, as strings. Warnings are non-fatal issues that the API discovered with your input. In general, they indicate that the List will not contain the all of the information you requested. You should fix the warnings and re-submit your request. */
    @Json(name = "warnings") val warnings: List<String>? = null,
) : ScryfallObject(ObjectType.ListObject)

// region Sets

/** [DOCS](https://scryfall.com/docs/api/sets) A Set object represents a group of related Magic cards. */
@JsonClass(generateAdapter = true)
data class MagicSet(
    /** A unique ID for this set on Scryfall that will not change. */
    @Json(name = "id") val id: String = "",
    /** The unique three to five-letter code for this set. */
    @Json(name = "code") val code: String = "",
    /** The unique code for this set on MTGO, which may differ from the regular code. */
    @Json(name = "mtgo_code") val mtgoCode: String = "",
    /** This set’s ID on TCGplayer’s API, also known as the groupId. */
    @Json(name = "tcgplayer_id") val tcgplayerId: Int = -1,
    /** The English name of the set. */
    @Json(name = "name") val name: String = "",
    /** A computer-readable classification for this set. See below. */
    @Json(name = "set_type") val setType: String = "",
    /** The date the set was released or the first card was printed in the set (in GMT-8 Pacific time). */
    @Json(name = "released_at") val releasedAt: String = "",
    /** The block code for this set, if any. */
    @Json(name = "block_code") val blockCode: String = "",
    /** The block or group name code for this set, if any. */
    @Json(name = "block") val block: String = "",
    /** The set code for the parent set, if any. promo and token sets often have a parent set. */
    @Json(name = "parent_set_code") val parentSetCode: String = "",
    /** The number of cards in this set. */
    @Json(name = "card_count") val cardCount: Int = -1,
    /** The denominator for the set’s printed collector numbers. */
    @Json(name = "printed_size") val printedSize: Int = -1,
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

// endregion

// region Card and dependencies

/** [DOCS](https://scryfall.com/docs/api/cards) Card objects represent individual Magic: The Gathering cards that players could obtain and add to their collection (with a few minor exceptions). */
@JsonClass(generateAdapter = true)
data class Card(
    // region Core Card Fields
    /** This card’s Arena ID, if any. A large percentage of cards are not available on Arena and do not have this ID. */
    @Json(name = "arena_id") val arenaId:  Int? = null,
    /** A unique ID for this card in Scryfall’s database. */
    @Json(name = "id") val id: String = "",
    /** A language code for this printing. */
    @Json(name = "lang") val lang: String = "",
    /** This card’s Magic Online ID (also known as the Catalog ID), if any. A large percentage of cards are not available on Magic Online and do not have this ID. */
    @Json(name = "mtgo_id") val mtgoId: Int? = null,
    /** This card’s foil Magic Online ID (also known as the Catalog ID), if any. A large percentage of cards are not available on Magic Online and do not have this ID. */
    @Json(name = "mtgo_foil_id") val mtgoFoilId: Int? = null,
    /** This card’s multiverse IDs on Gatherer, if any, as an array of integers. Note that Scryfall includes many promo cards, tokens, and other esoteric objects that do not have these identifiers. */
    @Json(name = "multiverse_ids") val multiverseIds: List<Int>? = null,
    /** This card’s ID on TCGplayer’s API, also known as the productId. */
    @Json(name = "tcgplayer_id") val tcgplayerId: Int? = null,
    /** This card’s ID on Cardmarket’s API, also known as the idProduct. */
    @Json(name = "cardmarket_id") val cardmarketId: Int? = null,
    /** A unique ID for this card’s oracle identity. This value is consistent across reprinted card editions, and unique among different cards with the same name (tokens, Unstable variants, etc). */
    @Json(name = "oracle_id") val oracleId: String = "",
    /** A link to where you can begin paginating all re/prints for this card on Scryfall’s API. */
    @Json(name = "prints_search_uri") val printsSearchUri: String = "",
    /** A link to this card’s rulings list on Scryfall’s API. */
    @Json(name = "rulings_uri") val rulingsUri: String = "",
    /** A link to this card’s permapage on Scryfall’s website. */
    @Json(name = "scryfall_uri") val scryfallUri: String = "",
    /** A link to this card object on Scryfall’s API. */
    @Json(name = "uri") val uri: String = "",
    // endregion
    // region Gameplay Fields
    /** If this card is closely related to other cards, this property will be an array with Related Card Objects. */
    @Json(name = "all_parts") val allParts: List<RelatedCard>? = null,
    /** An array of Card Face objects, if this card is multifaced. */
    @Json(name = "card_faces") val cardFaces: List<CardFace>? = null,
    /** The card’s converted mana cost. Note that some funny cards have fractional mana costs. */
    @Json(name = "cmc") val cmc: Float = 0.0f,
    /** This card’s color identity. */
    @Json(name = "color_identity") val colorIdentity: Set<Color> = emptySet(),
    /** The colors in this card’s color indicator, if any. A null value for this field indicates the card does not have one. */
    @Json(name = "color_indicator") val colorIndicator: Set<Color>? = null,
    /** This card’s colors, if the overall card has colors defined by the rules. Otherwise the colors will be on the card_faces objects, see below. */
    @Json(name = "colors") val colors: Set<Color>? = null,
    /** This card’s overall rank/popularity on EDHREC. Not all cards are ranked. */
    @Json(name = "edhrec_rank") val edhrecRank: Int? = null,
    /** True if this printing exists in a foil version. */
    @Json(name = "foil") val foil: Boolean = false,
    /** This card’s hand modifier, if it is Vanguard card. This value will contain a delta, such as -1. */
    @Json(name = "hand_modifier") val handModifier: String? = null,
    /** An array of keywords that this card uses, such as 'Flying' and 'Cumulative upkeep'. */
    @Json(name = "keywords") val keywords: List<String> = emptyList(),
    /** A code for this card’s layout. */
    @Json(name = "layout") val layout: String = "",
    /** An object describing the legality of this card across play formats. Possible legalities are legal, not_legal, restricted, and banned. */
    @Json(name = "legalities") val legalities: Map<String, Legality> = emptyMap(),
    /** This card’s life modifier, if it is Vanguard card. This value will contain a delta, such as +2. */
    @Json(name = "life_modifier") val lifeModifier: String? = null,
    /** This loyalty if any. Note that some cards have loyalties that are not numeric, such as X. */
    @Json(name = "loyalty") val loyalty: String? = null,
    /** The mana cost for this card. This value will be any empty string "" if the cost is absent. Remember that per the game rules, a missing mana cost and a mana cost of {0} are different values. Multi-faced cards will report this value in card faces. */
    @Json(name = "mana_cost") val manaCost: String? = null,
    /** The name of this card. If this card has multiple faces, this field will contain both names separated by ␣//␣. */
    @Json(name = "name") val name: String = "",
    /** True if this printing exists in a nonfoil version. */
    @Json(name = "nonfoil") val nonfoil: Boolean = false,
    /** The Oracle text for this card, if any. */
    @Json(name = "oracle_text") val oracleText: String? = null,
    /** True if this card is oversized. */
    @Json(name = "oversized") val oversized: Boolean = false,
    /** This card’s power, if any. Note that some cards have powers that are not numeric, such as *. */
    @Json(name = "power") val power: String? = null,
    /** Colors of mana that this card could produce. */
    @Json(name = "produced_mana") val producedMana: Set<Color>? = emptySet(),
    /** True if this card is on the Reserved List. */
    @Json(name = "reserved") val reserved: Boolean = false,
    /** This card’s toughness, if any. Note that some cards have toughnesses that are not numeric, such as *. */
    @Json(name = "toughness") val toughness: String? = null,
    /** The type line of this card. */
    @Json(name = "type_line") val typeLine: String = "",
    // endregion
    // region Print Fields
    /** The name of the illustrator of this card. Newly spoiled cards may not have this field yet. */
    @Json(name = "artist") val artist: String? = null,
    /** Whether this card is found in boosters. */
    @Json(name = "booster") val booster: Boolean = false,
    /** This card’s border color: black, borderless, gold, silver, or white. */
    @Json(name = "border_color") val borderColor: BorderColor = BorderColor.Black,
    /** The Scryfall ID for the card back design present on this card. */
    @Json(name = "card_back_id") val cardBackId: String = "",
    /** This card’s collector number. Note that collector numbers can contain non-numeric characters, such as letters or ★. */
    @Json(name = "collector_number") val collectorNumber: String = "",
    /** True if you should consider avoiding use of this print downstream. */
    @Json(name = "content_warning") val contentWarning: Boolean? = null,
    /** True if this card was only released in a video game. */
    @Json(name = "digital") val digital: Boolean = false,
    /** The just-for-fun name printed on the card (such as for Godzilla series cards). */
    @Json(name = "flavor_name") val flavorName: String? = null,
    /** The flavor text, if any. */
    @Json(name = "flavor_text") val flavorText: String? = null,
    /** This card’s frame effects, if any. */
    @Json(name = "frame_effects") val frameEffects: Set<FrameEffect>? = null,
    /** This card’s frame layout. */
    @Json(name = "frame") val frame: Frame = Frame.Original,
    /** True if this card’s artwork is larger than normal. */
    @Json(name = "full_art") val fullArt: Boolean = false,
    /** A list of games that this card print is available in, paper, arena, and/or mtgo. */
    @Json(name = "games") val games: Set<Games> = emptySet(),
    /** True if this card’s imagery is high resolution. */
    @Json(name = "highres_image") val highResImage: Boolean = false,
    /** A unique identifier for the card artwork that remains consistent across reprints. Newly spoiled cards may not have this field yet. */
    @Json(name = "illustration_id") val illustrationId: String = "",
    /** A computer-readable indicator for the state of this card’s image, one of missing, placeholder, lowres, or highres_scan. */
    @Json(name = "image_status") val imageStatus: ImageStatus = ImageStatus.Missing,
    /** An object listing available imagery for this card. See the Card Imagery article for more information. */
    @Json(name = "image_uris") val imageUris: ImageUris? = null,
    /** An object containing daily price information for this card, including usd, usd_foil, eur, and tix prices, as strings. */
    @Json(name = "prices") val prices: Prices = Prices(),
    /** The localized name printed on this card, if any. */
    @Json(name = "printed_name") val printedName: String? = null,
    /** The localized text printed on this card, if any. */
    @Json(name = "printed_text") val printedText: String? = null,
    /** The localized type line printed on this card, if any. */
    @Json(name = "printed_type_line") val printedTypeLine: String? = null,
    /** True if this card is a promotional print. */
    @Json(name = "promo") val promo: Boolean = false,
    /** An array of strings describing what categories of promo cards this card falls into. */
    @Json(name = "promo_types") val promoTypes: List<String>? = emptyList(),
    /** An object providing URIs to this card’s listing on major marketplaces. */
    @Json(name = "purchase_uris") val purchaseUris: Map<String, String> = emptyMap(),
    /** This card’s rarity. One of common, uncommon, rare, special, mythic, or bonus. */
    @Json(name = "rarity") val rarity: Rarity = Rarity.Common,
    /** An object providing URIs to this card’s listing on other Magic: The Gathering online resources. */
    @Json(name = "related_uris") val relatedUris: Map<String, String> = emptyMap(),
    /** The date this card was first released. */
    @Json(name = "released_at") val releasedAt: String = "",
    /** True if this card is a reprint. */
    @Json(name = "reprint") val reprint: Boolean = false,
    /** A link to this card’s set on Scryfall’s website. */
    @Json(name = "scryfall_set_uri") val scryfallSetUri: String = "",
    /** This card’s full set name. */
    @Json(name = "set_name") val setName: String = "",
    /** A link to where you can begin paginating this card’s set on the Scryfall API. */
    @Json(name = "set_search_uri") val setSearchUri: String = "",
    /** The type of set this printing is in. */
    @Json(name = "set_type") val setType: String = "",
    /** A link to this card’s set object on Scryfall’s API. */
    @Json(name = "set_uri") val setUri: String = "",
    /** This card’s set code. */
    @Json(name = "set") val set: String = "",
    /** True if this card is a Story Spotlight. */
    @Json(name = "story_spotlight") val storySpotlight: Boolean = false,
    /** True if the card is printed without text. */
    @Json(name = "textless") val textless: Boolean = false,
    /** Whether this card is a variation of another printing. */
    @Json(name = "variation") val variation: Boolean = false,
    /** The printing ID of the printing this card is a variation of. */
    @Json(name = "variation_of") val variationOf: String? = null,
    /** This card’s watermark, if any. */
    @Json(name = "watermark") val watermark: String? = null,
    /** The date this card was previewed. */
    @Json(name = "preview.previewed_at") val previewPreviewedAt: String? = null,
    /** A link to the preview for this card. */
    @Json(name = "preview.source_uri") val previewSourceUri: String? = null,
    /** The name of the source that previewed this card. */
    @Json(name = "preview.source") val previewSource: String? = null,
    // endregion
) : ScryfallObject(ObjectType.CardObject)

/** [DOCS](https://scryfall.com/docs/api/cards) Cards that are closely related to other cards (because they call them by name, or generate a token, or meld, etc) have a all_parts property that contains Related Card objects. */
@JsonClass(generateAdapter = true)
data class RelatedCard(
    /** An unique ID for this card in Scryfall’s database. */
    @Json(name = "id") val id: String = "",
    /** A field explaining what role this card plays in this relationship, one of token, meld_part, meld_result, or combo_piece. */
    @Json(name = "component") val component: Component = Component.Token,
    /** The name of this particular related card. */
    @Json(name = "name") val name: String = "",
    /** The type line of this card. */
    @Json(name = "type_line") val typeLine: String = "",
    /** A URI where you can retrieve a full object describing this card on Scryfall’s API. */
    @Json(name = "uri") val uri: String,
) : ScryfallObject(ObjectType.RelatedCardObject)

/** [DOCS](https://scryfall.com/docs/api/cards) Multiface cards have a card_faces property containing at least two Card Face objects. */
@JsonClass(generateAdapter = true)
data class CardFace(
    /** The name of the illustrator of this card face. Newly spoiled cards may not have this field yet. */
    @Json(name = "artist") val artist: String? = null,
    /** The colors in this face’s color indicator, if any. */
    @Json(name = "color_indicator") val colorIndicator: Set<Color>? = null,
    /** This face’s colors, if the game defines colors for the individual face of this card. */
    @Json(name = "colors") val colors: Set<Color>? = null,
    /** The flavor text printed on this face, if any. */
    @Json(name = "flavor_text") val flavorText: String? = null,
    /** A unique identifier for the card face artwork that remains consistent across reprints. Newly spoiled cards may not have this field yet. */
    @Json(name = "illustration_id") val illustrationId: String? = null,
    /** An object providing URIs to imagery for this face, if this is a double-sided card. If this card is not double-sided, then the image_uris property will be part of the parent object instead. */
    @Json(name = "image_uris") val imageUris: ImageUris? = null,
    /** This face’s loyalty, if any. */
    @Json(name = "loyalty") val loyalty: String? = null,
    /** The mana cost for this face. This value will be any empty string "" if the cost is absent. Remember that per the game rules, a missing mana cost and a mana cost of {0} are different values. */
    @Json(name = "mana_cost") val manaCost: String = "",
    /** The name of this particular face. */
    @Json(name = "name") val name: String = "",
    /** The Oracle text for this face, if any. */
    @Json(name = "oracle_text") val oracleText: String? = null,
    /** This face’s power, if any. Note that some cards have powers that are not numeric, such as *. */
    @Json(name = "power") val power: String? = null,
    /** The localized name printed on this face, if any. */
    @Json(name = "printed_name") val printedName: String? = null,
    /** The localized text printed on this face, if any. */
    @Json(name = "printed_text") val printedText: String? = null,
    /** The localized type line printed on this face, if any. */
    @Json(name = "printed_type_line") val printedTypeLine: String? = null,
    /** This face’s toughness, if any. */
    @Json(name = "toughness") val toughness: String? = null,
    /** The type line of this particular face. */
    @Json(name = "type_line") val typeLine: String = "",
    /** The watermark on this particular card face, if any. */
    @Json(name = "watermark") val watermark: String? = null,
) : ScryfallObject(ObjectType.CardFaceObject)

// endregion

// region Ruling

/** [DOCS](https://scryfall.com/docs/api/rulings) Rulings represent Oracle rulings, Wizards of the Coast set release notes, or Scryfall notes for a particular card. */
@JsonClass(generateAdapter = true)
data class Ruling(
    /** A computer-readable string indicating which company produced this ruling, either wotc or scryfall. */
    @Json(name = "source") val source: String = "",
    /** The date when the ruling or note was published. */
    @Json(name = "published_at") val publishedAt: String = "",
    /** The text of the ruling. */
    @Json(name = "comment") val comment: String = "",
) : ScryfallObject(ObjectType.RulingObject)

// endregion

// region CardSymbols

/** [DOCS](https://scryfall.com/docs/api/card-symbols) A Card Symbol object represents an illustrated symbol that may appear in card’s mana cost or Oracle text. */
@JsonClass(generateAdapter = true)
data class CardSymbol(
    /** The plaintext symbol. Often surrounded with curly braces {}. Note that not all symbols are ASCII text (for example, {∞}). */
    @Json(name = "symbol") val symbol: String = "",
    /** An alternate version of this symbol, if it is possible to write it without curly braces. */
    @Json(name = "loose_variant") val looseVariant: String? = null,
    /** An English snippet that describes this symbol. Appropriate for use in alt text or other accessible communication formats. */
    @Json(name = "english") val english: String = "",
    /** True if it is possible to write this symbol “backwards”. For example, the official symbol {U/P} is sometimes written as {P/U} or {P\U} in informal settings. Note that the Scryfall API never writes symbols backwards in other responses. This field is provided for informational purposes. */
    @Json(name = "transposable") val transposable: Boolean = false,
    /** True if this is a mana symbol. */
    @Json(name = "represents_mana") val representsMana: Boolean = false,
    /** A decimal number representing this symbol’s converted mana cost. Note that mana symbols from funny sets can have fractional converted mana costs. */
    @Json(name = "cmc") val cmc: Float? = 0.0f,
    /** True if this symbol appears in a mana cost on any Magic card. For example {20} has this field set to false because {20} only appears in Oracle text, not mana costs. */
    @Json(name = "appears_in_mana_costs") val appearsInManaCosts: Boolean = false,
    /** True if this symbol is only used on funny cards or Un-cards. */
    @Json(name = "funny") val funny: Boolean = false,
    /** An array of colors that this symbol represents. */
    @Json(name = "colors") val colors: Set<Color> = emptySet(),
    /** An array of plaintext versions of this symbol that Gatherer uses on old cards to describe original printed text. For example: {W} has ["oW", "ooW"] as alternates. */
    @Json(name = "gatherer_alternates") val gathererAlternates: List<String>? = null,
    /** A URI to an SVG image of this symbol on Scryfall’s CDNs. */
    @Json(name = "svg_uri") val svgUri: String? = null,
) : ScryfallObject(ObjectType.CardSymbolObject)

// endregion

// region Catalog

@JsonClass(generateAdapter = true)
data class Catalog(
    /** A link to the current catalog on Scryfall’s API. */
    @Json(name = "uri") val uri: String = "",
    /** The number of items in the data array. */
    @Json(name = "total_values") val total_values: Int = 0,
    /** An array of datapoints, as strings. */
    @Json(name = "data") val data: List<String> = emptyList(),
) : ScryfallObject(ObjectType.CatalogObject)

// endregion