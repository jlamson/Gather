package com.darkmoose117.gather.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.darkmoose117.gather.ui.components.GatherAppBar
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            Text("Search", style = MaterialTheme.typography.h1)
        }

    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ProvideWindowInsets(consumeWindowInsets = false) {
        GatherTheme {
            SearchScreen(Modifier.background(MaterialTheme.colors.surface))
        }
    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ProvideWindowInsets(consumeWindowInsets = false) {
        GatherTheme(darkTheme = true) {
            SearchScreen(Modifier.background(MaterialTheme.colors.surface))
        }
    }
}