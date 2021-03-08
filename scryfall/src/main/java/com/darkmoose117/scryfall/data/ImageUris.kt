package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUris(
    /** A transparent, rounded full card PNG. This is the best image to use for videos or other high-quality content.
     * * Size: 745 × 1040
     * * Type: PNG
     */
    @Json(name = "png") val png: String? = null,
    /** A full card image with the rounded corners and the majority of the border cropped off. Designed for dated contexts where rounded images can’t be used.
     * * Size: 480 × 680
     * * Type: JPG
     */
    @Json(name = "border_crop") val borderCrop: String? = null,
    /** A rectangular crop of the card’s art only. Not guaranteed to be perfect for cards with outlier designs or strange frame arrangements
     * * Size: Varies
     * * Type: JPG
     */
    @Json(name = "art_crop") val artCrop: String? = null,
    /** A large full card image
     * * Size: 672 × 936
     * * Type: JPG
     */
    @Json(name = "large") val large: String? = null,
    /** A medium-sized full card image
     * * Size: 488 × 680
     * * Type: JPG
     */
    @Json(name = "normal") val normal: String? = null,
    /** A small full card image. Designed for use as thumbnail or list icon.
     * * Size: 146 × 204
     * * Type: JPG
     */
    @Json(name = "small") val small: String? = null,
)