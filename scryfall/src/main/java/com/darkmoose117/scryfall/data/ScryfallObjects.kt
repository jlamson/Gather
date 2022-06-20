package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Suppress("UNUSED_PARAMETER")
sealed class ScryfallObject(
    @Json(name = "object") val objectType: ObjectType
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
