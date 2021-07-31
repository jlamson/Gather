package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.util.ThemedPreview

@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Box(modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.loading),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}


@Preview
@Composable
fun LightLoadingScreen() {
    ThemedPreview {
        LoadingCard()
    }
}

@Preview
@Composable
fun DarkLoadingScreen() {
    ThemedPreview(darkTheme = true) {
        LoadingCard()
    }
}