package com.darkmoose117.gather.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darkmoose117.gather.ui.cards.CardsViewType
import com.darkmoose117.gather.ui.cards.SymbolText
import com.darkmoose117.gather.util.ThemedPreview
import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.ImageUriSize
import com.darkmoose117.scryfall.preview.SpecificCards

@Composable
fun CardListItem(card: Card, viewType: CardsViewType, modifier: Modifier = Modifier) {
    when (viewType) {
        CardsViewType.Text -> CardText(card = card, modifier = modifier)
        CardsViewType.Image -> CardImage(card = card, modifier = modifier)
    }
}

@Composable
fun CardText(card: Card, modifier: Modifier = Modifier) {
    Card(modifier, elevation = 2.dp) {
        Column(Modifier.padding(8.dp)) {
            SymbolText(
                text = card.manaCost ?: "(none)",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.body1
            )

            SymbolText(
                text = card.name,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )

            SymbolText(text = card.typeLine, style = MaterialTheme.typography.subtitle2)

            card.oracleText?.let {
                SymbolText(text = it, style = MaterialTheme.typography.body1)
            }

            val numberText = if (!card.power.isNullOrBlank() || !card.toughness.isNullOrBlank()) {
                "${card.power}/${card.toughness}"
            } else if (!card.loyalty.isNullOrBlank()) {
                "${card.loyalty}"
            } else null

            if (!numberText.isNullOrBlank()) {
                SymbolText(
                    modifier = Modifier.align(Alignment.End),
                    text = numberText,
                    style = MaterialTheme.typography.subtitle1
                )
            }


        }
    }
}

@Composable
fun CardImage(card: Card, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val widthInPx = LocalDensity.current.run { maxWidth.toPx().toInt() }
        val imageSize = ImageUriSize.largestForWidth(widthInPx)
        Image(
            painter = rememberImagePainter(card.imageUris?.forSize(imageSize)),
            contentDescription = card.name,
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(imageSize.ratio)
        )
    }
}

@ExperimentalAnimationApi
@Preview(widthDp = 360)
@Composable
fun CardListItemPreview() {
    ThemedPreview {
        CardText(card = SpecificCards.AvacynArchangel, Modifier.padding(16.dp))
    }
}

@ExperimentalAnimationApi
@Preview(widthDp = 360)
@Composable
fun CardImageItemPreview() {
    ThemedPreview {
        CardImage(card = SpecificCards.AvacynArchangel, Modifier.padding(16.dp))
    }
}
