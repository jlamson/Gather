package com.darkmoose117.gather.ui.sets

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
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun SetsScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        GatherAppBar(
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            onNavIconPressed = { },
            title = { Text("Sets") },
            actions = { }
        )
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            Text("Sets", style = MaterialTheme.typography.h1)
        }

    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ProvideWindowInsets(consumeWindowInsets = false) {
        GatherTheme {
            SetsScreen(Modifier.background(MaterialTheme.colors.surface))
        }
    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ProvideWindowInsets(consumeWindowInsets = false) {
        GatherTheme(darkTheme = true) {
            SetsScreen(Modifier.background(MaterialTheme.colors.surface))
        }
    }
}