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

}
