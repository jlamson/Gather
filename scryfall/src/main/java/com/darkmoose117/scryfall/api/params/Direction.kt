package com.darkmoose117.scryfall.api.params

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    Direction.AUTO,
    Direction.ASC,
    Direction.DESC
)
annotation class DirectionString

/**
 * Then you can optionally specify a dir parameter to choose which direction the sorting should occur:
 */
object Direction {
    /**
     * Default. Scryfall will automatically choose the most inuitive direction to sort
     */
    const val AUTO = "auto"

    /**
     * Sort ascending (the direction of the arrows in the previous table)
     */
    const val ASC = "asc"

    /**
     * Sort descending (flip the direction of the arrows in the previous table)
     */
    const val DESC = "desc"
}

