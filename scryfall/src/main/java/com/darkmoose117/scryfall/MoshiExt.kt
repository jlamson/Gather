package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.ObjectType
import com.darkmoose117.scryfall.data.ScryfallObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory

fun Moshi.Builder.customizeForScryfall(): Moshi.Builder {
    add(buildScryfallObjectAdapterFactory())
    return this
}

fun buildScryfallObjectAdapterFactory(): PolymorphicJsonAdapterFactory<ScryfallObject> {
    val builder = PolymorphicJsonAdapterFactory.of(ScryfallObject::class.java, "object")

    ObjectType.values().forEach {
        builder.withSubtype(it.type, it.jsonName)
    }

    return builder
}