package com.darkmoose117.gather.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.util.ARCHANGEL_AVACYN
import com.darkmoose117.gather.util.ThemedPreview
import com.darkmoose117.gather.util.onLongPress
import io.magicthegathering.kotlinsdk.model.card.MtgCard

@ExperimentalAnimationApi
@Composable
fun CardListItem(card: MtgCard, modifier: Modifier = Modifier) {
    Card(modifier, elevation = 2.dp) {
        Column(Modifier.padding(8.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = card.name,
                    modifier = Modifier.weight(1f).alignByBaseline(),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = card.manaCost,
                    modifier = Modifier.alignByBaseline(),
                    style = MaterialTheme.typography.body1
                )
            }

            Text(text = card.type, style = MaterialTheme.typography.subtitle2)

            card.text?.let {
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
@Preview(widthDp = 360)
@Composable
fun CardListItemPreview() {
    ThemedPreview {
        CardListItem(card = ARCHANGEL_AVACYN, Modifier.padding(16.dp))
    }
}
