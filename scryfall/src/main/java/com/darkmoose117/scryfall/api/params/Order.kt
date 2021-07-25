package com.darkmoose117.scryfall.api.params

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    Order.NAME,
    Order.SET,
    Order.RELEASED,
    Order.RARITY,
    Order.COLOR,
    Order.USD,
    Order.TIX,
    Order.EUR,
    Order.CMC,
    Order.POWER,
    Order.TOUGHNESS,
    Order.EDHREC,
    Order.ARTIST
)
annotation class OrderString

object Order {
    /**
     * Default. Sort cards by name, A → Z
     */
    const val NAME: String = "name"

    /**
     * Sort cards by their set and collector number: AAA/#1 → ZZZ/#999
     */
    const val SET: String = "set"

    /**
     * Sort cards by their release date: Newest → Oldest
     */
    const val RELEASED: String = "released"

    /**
     * Sort cards by their rarity: Common → Mythic
     */
    const val RARITY: String = "rarity"

    /**
     * Sort cards by their color and color identity: WUBRG → multicolor → colorless
     */
    const val COLOR: String = "color"

    /**
     * Sort cards by their lowest known U.S. Dollar price: 0.01 → highest, null last
     */
    const val USD: String = "usd"

    /**
     * Sort cards by their lowest known TIX price: 0.01 → highest, null last
     */
    const val TIX: String = "tix"

    /**
     * Sort cards by their lowest known Euro price: 0.01 → highest, null last
     */
    const val EUR: String = "eur"

    /**
     * Sort cards by their converted mana cost: 0 → highest
     */
    const val CMC: String = "cmc"

    /**
     * Sort cards by their power: null → highest
     */
    const val POWER: String = "power"

    /**
     * Sort cards by their toughness: null → highest
     */
    const val TOUGHNESS: String = "toughness"

    /**
     * Sort cards by their EDHREC ranking: lowest → highest
     */
    const val EDHREC: String = "edhrec"

    /**
     * Sort cards by their front-side artist name: A → Z
     */
    const val ARTIST: String = "artist"
}