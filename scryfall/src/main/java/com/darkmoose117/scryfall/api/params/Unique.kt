package com.darkmoose117.scryfall.api.params

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    Unique.CARDS,
    Unique.ART,
    Unique.PRINTS
)
annotation class UniqueString

/**
 * The unique parameter specifies if Scryfall should remove “duplicate” results in your query. The options are:
 */
object Unique {
    /**
     * Default. Removes duplicate gameplay objects (cards that share a name and have the same functionality). For example, if your search matches more than one print of Pacifism, only one copy of Pacifism will be returned.
     */
    const val CARDS: String = "cards"

    /**
     * Returns only one copy of each unique artwork for matching cards. For example, if your search matches more than one print of Pacifism, one card with each different illustration for Pacifism will be returned, but any cards that duplicate artwork already in the results will be omitted.
     */
    const val ART: String = "art"

    /**
     * Returns all prints for all cards matched (disables rollup). For example, if your search matches more than one print of Pacifism, all matching prints will be returned.
     */
    const val PRINTS: String = "prints"
}