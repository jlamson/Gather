package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.DataList
import com.darkmoose117.scryfall.data.ScryfallObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.io.File

class ScryfallObjectParsingTests {

    companion object {

        lateinit var jsonAsString: String

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            val classLoader = this.javaClass.classLoader
            val file = classLoader.getResource("order_cmc_c_red_pow_3.json")?.let { url ->
                File(url.file)
            }
            assertNotNull(file)
            assert(file!!.exists())

            jsonAsString = file.readText()
        }
    }

    lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .customizeForScryfall()
            .build()
    }

    @Test
    fun `should not crash on any card`() {
        val responseType = Types.newParameterizedType(DataList::class.java, Card::class.java)
        val adapter: JsonAdapter<DataList<Card>> = moshi.adapter(responseType)
        val cardList = adapter.fromJson(jsonAsString)

        assertEquals(511, cardList?.totalCards)
    }


}