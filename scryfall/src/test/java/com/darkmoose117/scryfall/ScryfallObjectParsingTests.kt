package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.Card
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

    private fun loadFileIntoString(fileName: String): String {
        val classLoader = this.javaClass.classLoader
        val file = classLoader.getResource(fileName)?.let { url ->
            File(url.file)
        }
        assertNotNull(file)
        assert(file!!.exists())

        val jsonAsString = file.readText()
        assert(jsonAsString.isNotBlank())

        return jsonAsString
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
}