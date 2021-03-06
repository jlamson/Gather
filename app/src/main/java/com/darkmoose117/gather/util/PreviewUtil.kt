package com.darkmoose117.gather.util

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import io.magicthegathering.kotlinsdk.model.card.MtgCardLegality
import io.magicthegathering.kotlinsdk.model.card.MtgCardRuling
import org.joda.time.DateTime

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    ProvideWindowInsets(consumeWindowInsets = false) {
        GatherTheme(darkTheme = darkTheme) {
            Surface {
                content()
            }
        }
    }
}

val ARCHANGEL_AVACYN = MtgCard(
    id = "1",
    name = "Archangel Avacyn",
    names = listOf(
        "Archangel Avacyn",
        "Avacyn, the Purifier"
    ),
    manaCost = "{3}{W}{W}",
    cmc = 5,
    colors = listOf("White"),
    colorIdentity = listOf("W"),
    type = "Legendary Creature — Angel",
    supertypes = listOf("Legendary"),
    types = listOf("Creature"),
    subtypes = listOf("Angel"),
    rarity = "Mythic Rare",
    set = "SOI",
    setName = "Magic 2010",
    text = "Flash\\nFlying, vigilance\\nWhen Archangel Avacyn enters the battlefield, " +
            "creatures you control gain indestructible until end of turn.\\nWhen a " +
            "non-Angel creature you control dies, transform Archangel Avacyn at the " +
            "beginning of the next upkeep.",
    artist = "James Ryman",
    number = "5a",
    power = "4",
    toughness = "4",
    loyalty = null,
    multiverseid = 409741,
    imageUrl = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=409741&type=card",
    layout = "double-faced",
    legalities = listOf(
        MtgCardLegality("Modern", "Legal")
    ),
    rulings = listOf(
        MtgCardRuling(DateTime.now(), "Ruling text here")
    ),
    foreignNames = emptyList(),
)