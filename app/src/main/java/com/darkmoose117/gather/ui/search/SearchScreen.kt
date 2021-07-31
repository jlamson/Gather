package com.darkmoose117.gather.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.darkmoose117.gather.util.ThemedPreview


@Composable
fun SearchScreen(
    navController: NavController,
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
    ThemedPreview() {
        SearchScreen(rememberNavController(), Modifier.background(MaterialTheme.colors.surface))
    }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ThemedPreview(darkTheme = true) {
        SearchScreen(rememberNavController(), Modifier.background(MaterialTheme.colors.surface))
    }
}