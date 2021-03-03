package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.darkmoose117.gather.ui.theme.elevatedSurface

@Composable
fun GatherAppBar(
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    // This bar is translucent but elevation overlays are not applied to translucent colors.
    // Instead we manually calculate the elevated surface color from the opaque color,
    // then apply our alpha.
    //
    // We set the background on the Column rather than the TopAppBar,
    // so that the background is drawn behind any padding set on the app bar (i.e. status bar).
    val backgroundColor = MaterialTheme.colors.elevatedSurface(3.dp)
    Column(
        Modifier.background(backgroundColor.copy(alpha = 0.95f))
    ) {
        TopAppBar(
            modifier = modifier,
            backgroundColor = Color.Transparent,
            elevation = 0.dp, // No shadow needed
            contentColor = MaterialTheme.colors.onSurface,
            actions = actions,
            title = { Row { title() } }, // https://issuetracker.google.com/168793068
            navigationIcon = {
                Box(Modifier.fillMaxSize().padding(horizontal = 4.dp).clickable(onClick = onNavIconPressed)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_planeswalker_48_24_blue),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
        Divider()
    }
}

@Preview
@Composable
fun GatherAppBarPreview() {
    GatherTheme {
        GatherAppBar(title = { Text("Preview!") })
    }
}

@Preview
@Composable
fun GatherAppBarPreviewDark() {
    GatherTheme(darkTheme = true) {
        GatherAppBar(title = { Text("Preview!") })
    }
}