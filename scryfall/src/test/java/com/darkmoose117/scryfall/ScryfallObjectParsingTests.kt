package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.CardSymbol
import com.darkmoose117.scryfall.data.Catalog
import com.darkmoose117.scryfall.data.DataList
import com.darkmoose117.scryfall.data.Ruling
import com.darkmoose117.scryfall.data.MagicSet
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.File

class ScryfallObjectParsingTests {

    lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .customizeForScryfall()
            .build()
    }

    @Test
    fun `should not crash on any card`() {
        val jsonAsString = loadFileIntoString("order_cmc_c_red_pow_3.json")

        val responseType = Types.newParameterizedType(DataList::class.java, Card::class.java)
        val adapter: JsonAdapter<DataList<Card>> = moshi.adapter(responseType)
        val cardList = adapter.fromJson(jsonAsString)

        assertEquals(511, cardList?.totalCards)
    }

    @Test
    fun `should not crash on any set`() {
        val jsonAsString = loadFileIntoString("all_sets.json")

        val responseType = Types.newParameterizedType(DataList::class.java, MagicSet::class.java)
        val adapter: JsonAdapter<DataList<MagicSet>> = moshi.adapter(responseType)
        val allSets = adapter.fromJson(jsonAsString)

        assertNotNull(allSets?.data?.find { it.code.equals("khm", ignoreCase = true) })
    }

    @Test
    fun `should not crash on any rulings`() {
        val jsonAsString = loadFileIntoString("cma_176_rulings.json")

        val responseType = Types.newParameterizedType(DataList::class.java, Ruling::class.java)
        val adapter: JsonAdapter<DataList<Ruling>> = moshi.adapter(responseType)
        val rulings = adapter.fromJson(jsonAsString)

        assertEquals(3, rulings?.data?.size)
    }

    @Test
    fun `should not crash on any symbols`() {
        val jsonAsString = loadFileIntoString("all_symbols.json")

        val responseType = Types.newParameterizedType(DataList::class.java, CardSymbol::class.java)
        val adapter: JsonAdapter<DataList<CardSymbol>> = moshi.adapter(responseType)
        val symbols = adapter.fromJson(jsonAsString)

        assertEquals(true, symbols?.data?.find { it.symbol == "{2/W}" }?.representsMana)
    }

    @Test
    fun `should not crash on any catalogs`() {
        val jsonAsString = loadFileIntoString("catalog_land_types.json")

        val adapter = moshi.adapter(Catalog::class.java)
        val catalog = adapter.fromJson(jsonAsString)

        assertEquals("https://api.scryfall.com/catalog/land-types", catalog?.uri)
        assertEquals(13, catalog?.data?.size)
    }
}