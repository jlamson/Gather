package com.darkmoose117.gather.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darkmoose117.gather.util.ThemedPreview
import com.darkmoose117.scryfall.data.Card
import com.darkmoose117.scryfall.data.ImageUriSize
import com.darkmoose117.scryfall.data.ImageUris
import com.darkmoose117.scryfall.preview.SpecificCards
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun CardTextListItem(card: Card, modifier: Modifier = Modifier) {
    Card(modifier, elevation = 2.dp) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = card.manaCost ?: "(none)",
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.body1
            )

            Text(
                text = card.name,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )

            Text(text = card.typeLine, style = MaterialTheme.typography.subtitle2)

            card.oracleText?.let {
                Text(text = it, style = MaterialTheme.typography.body1)
            }

            val numberText = if (!card.power.isNullOrBlank() || !card.toughness.isNullOrBlank()) {
                "${card.power}/${card.toughness}"
            } else if (!card.loyalty.isNullOrBlank()) {
                "${card.loyalty}"
            } else null

            if (!numberText.isNullOrBlank()) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = numberText,
                    style = MaterialTheme.typography.subtitle1
                )
            }


        }
    }
}

@ExperimentalAnimationApi
@Composable
fun CardImageListItem(card: Card, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val widthInPx = LocalDensity.current.run { maxWidth.toPx().toInt() }
        val imageSize = ImageUriSize.largestForWidth(widthInPx)
        Image(
            painter = rememberImagePainter(card.imageUris?.forSize(imageSize)),
            contentDescription = card.name,
            modifier = Modifier.fillMaxWidth(1f).aspectRatio(imageSize.ratio)
        )
    }
}

@ExperimentalAnimationApi
@Preview(widthDp = 360)
@Composable
fun CardListItemPreview() {
    ThemedPreview {
        CardTextListItem(card = SpecificCards.AvacynArchangel, Modifier.padding(16.dp))
    }
}

@ExperimentalAnimationApi
@Preview(widthDp = 360)
@Composable
fun CardImageItemPreview() {
    ThemedPreview {
        CardImageListItem(card = SpecificCards.AvacynArchangel, Modifier.padding(16.dp))
    }
}
