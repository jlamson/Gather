package com.darkmoose117.scryfall.api.cards

import com.darkmoose117.scryfall.api.params.Direction
import com.darkmoose117.scryfall.api.params.DirectionString
import com.darkmoose117.scryfall.api.params.Format
import com.darkmoose117.scryfall.api.params.FormatString
import com.darkmoose117.scryfall.api.params.Order
import com.darkmoose117.scryfall.api.params.OrderString
import com.darkmoose117.scryfall.api.params.Unique
import com.darkmoose117.scryfall.api.params.UniqueString
import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.DataList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScryfallCardsApi {

    /**
     * @param query A fulltext search query. Make sure that your parameter is properly encoded.
     * @param unique The strategy for omitting similar cards. See [Unique]
     * @param order The method to sort returned cards. See [Order]
     * @param dir The direction to sort cards. See [Direction]
     * @param includeExtras If true, extra cards (tokens, planes, etc) will be included. Equivalent to adding include:extras to the fulltext search. Defaults to false.
     * @param includeMultilingual If true, cards in every language supported by Scryfall will be included. Defaults to false.
     * @param includeVariations If true, rare care variants will be included, like the Hairy Runesword. Defaults to false.
     * @param page The page number to return, default 1.
     * @param format The data format to return: json or csv. Defaults to json. see [Format]
     * @param pretty If true, the returned JSON will be prettified. Avoid using for production code.
     *
     * @author [Scryfall Docs](https://scryfall.com/docs/api/cards/search)
     */
    @GET("cards/search")
    suspend fun getCardsBySearch(
        @Query("q") query: String,
        @Query("unique") @UniqueString unique: String? = null,
        @Query("order") @OrderString order: String? = null,
        @Query("dir") @DirectionString dir: String? = null,
        @Query("include_extras") includeExtras: Boolean? = null,
        @Query("include_multilingual") includeMultilingual: Boolean? = null,
        @Query("include_variations") includeVariations: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("format") @FormatString format: String? = null,
        @Query("pretty") pretty: Boolean? = null
    ) : Response<DataList<Card>>

    /**
     * @param id The Scryfall ID.
     * @param format The data format to return: json, text, or image. Defaults to json.
     * @param face If using the image format and this parameter has the value back, the back face of the card will be returned. Will return a 422 if this card has no back face.
     * @param version The image version to return when using the image format: small, normal, large, png, art_crop, or border_crop. Defaults to large.
     * @param pretty If true, the returned JSON will be prettified. Avoid using for production code.
     *
     * @author [Scryfall Docs](https://scryfall.com/docs/api/cards/id)
     */
    @GET("cards/{id}")
    suspend fun getCardById(
        @Path("id") id: String,
        @Query("format") format: String? = null,
        @Query("face") face: String? = null,
        @Query("version") version: String? = null,
        @Query("pretty") pretty: Boolean? = null,
    ) : Response<Card>

    companion object {
        const val PAGE_SIZE = 175
    }

}
