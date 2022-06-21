package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.BorderColor
import com.darkmoose117.scryfall.data.Color
import com.darkmoose117.scryfall.data.Component
import com.darkmoose117.scryfall.data.Frame
import com.darkmoose117.scryfall.data.FrameEffect
import com.darkmoose117.scryfall.data.Games
import com.darkmoose117.scryfall.data.ImageStatus
import com.darkmoose117.scryfall.data.Legality
import com.darkmoose117.scryfall.data.Rarity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EnumAdapterTests {
    
    lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .customizeForScryfall()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    
    @Test
    fun `should load defaults for all Card enums`() {
        val out: PileOfEnums = moshi.adapter(PileOfEnums::class.java).fromJson(pileOfEnumsJson)!!
        assertEquals(BorderColor.Black, out.goodBorderColor)
        assertEquals(BorderColor.NotHandled, out.badBorderColor)
        assertEquals(Color.Black, out.goodColor)
        assertEquals(Color.NotHandled, out.badColor)
        assertEquals(Component.Token, out.goodComponent)
        assertEquals(Component.NotHandled, out.badComponent)
        assertEquals(Frame.Holofoil, out.goodFrame)
        assertEquals(Frame.NotHandled, out.badFrame)
        assertEquals(FrameEffect.Lesson, out.goodFrameEffect)
        assertEquals(FrameEffect.NotHandled, out.badFrameEffect)
        assertEquals(Games.Mtgo, out.goodGames)
        assertEquals(Games.NotHandled, out.badGames)
        assertEquals(ImageStatus.HighResScan, out.goodImageStatus)
        assertEquals(ImageStatus.NotHandled, out.badImageStatus)
        assertEquals(Legality.Legal, out.goodLegality)
        assertEquals(Legality.NotHandled, out.badLegality)
        assertEquals(Rarity.Common, out.goodRarity)
        assertEquals(Rarity.NotHandled, out.badRarity)
    }
    
}

data class PileOfEnums(
    @Json(name = "good_border_color") val goodBorderColor: BorderColor,
    @Json(name = "bad_border_color") val badBorderColor: BorderColor,
    @Json(name = "good_color") val goodColor: Color,
    @Json(name = "bad_color") val badColor: Color,
    @Json(name = "good_component") val goodComponent: Component,
    @Json(name = "bad_component") val badComponent: Component,
    @Json(name = "good_frame") val goodFrame: Frame,
    @Json(name = "bad_frame") val badFrame: Frame,
    @Json(name = "good_frame_effect") val goodFrameEffect: FrameEffect,
    @Json(name = "bad_frame_effect") val badFrameEffect: FrameEffect,
    @Json(name = "good_games") val goodGames: Games,
    @Json(name = "bad_games") val badGames: Games,
    @Json(name = "good_image_status") val goodImageStatus: ImageStatus,
    @Json(name = "bad_image_status") val badImageStatus: ImageStatus,
    @Json(name = "good_legality") val goodLegality: Legality,
    @Json(name = "bad_legality") val badLegality: Legality,
    @Json(name = "good_rarity") val goodRarity: Rarity,
    @Json(name = "bad_rarity") val badRarity: Rarity,
)

val pileOfEnumsJson: String = """{
    "good_border_color": "black",
    "bad_border_color": "fooBlahBadValue",
    "good_color": "B",
    "bad_color": "fooBlahBadValue",
    "good_component": "token",
    "bad_component": "fooBlahBadValue",
    "good_frame": "2015",
    "bad_frame": "fooBlahBadValue",
    "good_frame_effect": "lesson",
    "bad_frame_effect": "fooBlahBadValue",
    "good_games": "mtgo",
    "bad_games": "fooBlahBadValue",
    "good_image_status": "highres_scan",
    "bad_image_status": "fooBlahBadValue",
    "good_legality": "legal",
    "bad_legality": "fooBlahBadValue",
    "good_rarity": "common",
    "bad_rarity": "fooBlahBadValue"
}        
""".trimIndent()