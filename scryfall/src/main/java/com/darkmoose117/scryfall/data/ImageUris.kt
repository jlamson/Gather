package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * An object holding all available image URIs for this card.
 *
 * @see ImageUriSize
 */
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
) {
    /**
     * return the member corresponding to the [size] provided. Example [ImageUris.png] for [ImageUriSize.Png]
     */
    fun forSize(size: ImageUriSize) = when (size) {
        ImageUriSize.Png -> this.png
        ImageUriSize.BorderCrop -> this.borderCrop
        ImageUriSize.Large -> this.large
        ImageUriSize.Normal -> this.normal
        ImageUriSize.Small -> this.small
    }
}

/**
 * A simple helper to describe the size of images returned by various [ImageUriSize]s.
 *
 * @
 */
sealed class ImageUriSize(val width: Int, val height: Int) {

    val ratio: Float = width.toFloat() / height.toFloat()

    object Png : ImageUriSize(745, 1040)
    object BorderCrop : ImageUriSize(480, 680)
    object Large : ImageUriSize(672, 936)
    object Normal : ImageUriSize(488, 680)
    object Small : ImageUriSize(146, 204)

    companion object {
        private val bigToSmall by lazy {
            listOf(Png, BorderCrop, Large, Normal, Small).sortedByDescending(ImageUriSize::width)
        }

        /**
         * Returns one of [Png], [Large], [Normal], [Small] where the width of the image will fill
         * the provided [width]. If
         */
        fun largestForWidth(viewWidth: Int) = bigToSmall.firstOrNull { it.width >= viewWidth } ?: Png
    }
}