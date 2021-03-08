package com.darkmoose117.scryfall.data

import com.squareup.moshi.Json

enum class FrameEffect {
    /** The cards have a legendary crown */
    @Json(name = "legendary") Legendary,
    /** The miracle frame effect */
    @Json(name = "miracle") Miracle,
    /** The Nyx-touched frame effect */
    @Json(name = "nyxtouched") NyxTouched,
    /** The draft-matters frame effect */
    @Json(name = "draft") Draft,
    /** The Devoid frame effect */
    @Json(name = "devoid") Devoid,
    /** The Odyssey tombstone mark */
    @Json(name = "tombstone") Tombstone,
    /** A colorshifted frame */
    @Json(name = "colorshifted") ColorShifted,
    /** The FNM-style inverted frame */
    @Json(name = "inverted") Inverted,
    /** The sun and moon transform marks */
    @Json(name = "sunmoondfc") SunMoonDoubleFace,
    /** The compass and land transform marks */
    @Json(name = "compasslanddfc") CompassLandDoubleFace,
    /** The Origins and planeswalker transform marks */
    @Json(name = "originpwdfc") OriginPwDoubleFace,
    /** The moon and Eldrazi transform marks */
    @Json(name = "mooneldrazidfc") MoonEldraziDoubleFace,
    /** The waxing and waning crescent moon transform marks */
    @Json(name = "waxingandwaningmoondfc") WaxingAndWaningMoonDoubleFace,
    /** A custom Showcase frame */
    @Json(name = "showcase") Showcase,
    /** An extended art frame */
    @Json(name = "extendedart") ExtendedArt,
    /** The cards have a companion frame */
    @Json(name = "companion") Companion,
    /** The cards have an etched foil treatment */
    @Json(name = "etched") Etched,
    /** The cards have the snowy frame effect */
    @Json(name = "snow") Snow,
}