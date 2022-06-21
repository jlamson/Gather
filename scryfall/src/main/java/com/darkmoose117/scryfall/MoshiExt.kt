package com.darkmoose117.scryfall

import com.darkmoose117.scryfall.data.BorderColor
import com.darkmoose117.scryfall.data.Color
import com.darkmoose117.scryfall.data.Component
import com.darkmoose117.scryfall.data.Frame
import com.darkmoose117.scryfall.data.FrameEffect
import com.darkmoose117.scryfall.data.Games
import com.darkmoose117.scryfall.data.ImageStatus
import com.darkmoose117.scryfall.data.Legality
import com.darkmoose117.scryfall.data.ObjectType
import com.darkmoose117.scryfall.data.Rarity
import com.darkmoose117.scryfall.data.ScryfallObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory

fun Moshi.Builder.customizeForScryfall(): Moshi.Builder = this.apply {
    add(buildScryfallObjectAdapterFactory())
    add(BorderColor::class.java, buildBorderColorEnumAdapter())
    add(Color::class.java, buildColorEnumAdapter())
    add(Component::class.java, buildComponentEnumAdapter())
    add(Frame::class.java, buildFrameEnumAdapter())
    add(FrameEffect::class.java, buildFrameEffectEnumAdapter())
    add(Games::class.java, buildGamesEnumAdapter())
    add(ImageStatus::class.java, buildImageStatusEnumAdapter())
    add(Legality::class.java, buildLegalityEnumAdapter())
    add(Rarity::class.java, buildRarityEnumAdapter())
}

fun buildScryfallObjectAdapterFactory(): PolymorphicJsonAdapterFactory<ScryfallObject> =
    PolymorphicJsonAdapterFactory
        .of(ScryfallObject::class.java, "object")
        .apply {
            ObjectType.values().forEach {
                withSubtype(it.type, it.jsonName)
            }
        }

fun buildBorderColorEnumAdapter(
    fallback: BorderColor = BorderColor.NotHandled
): EnumJsonAdapter<BorderColor> = EnumJsonAdapter.create(BorderColor::class.java)
    .withUnknownFallback(fallback)

fun buildColorEnumAdapter(
    fallback: Color = Color.NotHandled
): EnumJsonAdapter<Color> = EnumJsonAdapter.create(Color::class.java)
    .withUnknownFallback(fallback)

fun buildComponentEnumAdapter(
    fallback: Component = Component.NotHandled
): EnumJsonAdapter<Component> = EnumJsonAdapter.create(Component::class.java)
    .withUnknownFallback(fallback)

fun buildFrameEnumAdapter(
    fallback: Frame = Frame.NotHandled
): EnumJsonAdapter<Frame> = EnumJsonAdapter.create(Frame::class.java)
    .withUnknownFallback(fallback)

fun buildFrameEffectEnumAdapter(
    fallback: FrameEffect = FrameEffect.NotHandled
): EnumJsonAdapter<FrameEffect> = EnumJsonAdapter.create(FrameEffect::class.java)
    .withUnknownFallback(fallback)

fun buildGamesEnumAdapter(
    fallback: Games = Games.NotHandled
): EnumJsonAdapter<Games> = EnumJsonAdapter.create(Games::class.java)
    .withUnknownFallback(fallback)

fun buildImageStatusEnumAdapter(
    fallback: ImageStatus = ImageStatus.NotHandled
): EnumJsonAdapter<ImageStatus> = EnumJsonAdapter.create(ImageStatus::class.java)
    .withUnknownFallback(fallback)

fun buildLegalityEnumAdapter(
    fallback: Legality = Legality.NotHandled
): EnumJsonAdapter<Legality> = EnumJsonAdapter.create(Legality::class.java)
    .withUnknownFallback(fallback)

fun buildRarityEnumAdapter(
    fallback: Rarity = Rarity.NotHandled
): EnumJsonAdapter<Rarity> = EnumJsonAdapter.create(Rarity::class.java)
    .withUnknownFallback(fallback)
