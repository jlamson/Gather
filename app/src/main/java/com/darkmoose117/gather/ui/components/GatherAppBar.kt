package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.theme.GatherTheme

/**
 * A wrapper around [TopAppBar] which allows some [bottomContent] below the bar, but within the same
 * surface. This is useful for tabs.
 */
@Composable
fun TopAppBarWithBottomContent(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
    contentPadding: PaddingValues? = null,
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Column(contentPadding?.let { Modifier.padding(it) } ?: Modifier) {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                backgroundColor = Color.Transparent,
                contentColor = LocalContentColor.current,
                elevation = 0.dp,
            )

            bottomContent?.invoke()
        }
    }
}

@Composable
fun NavigateUpIcon(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(modifier = modifier, onClick = navigateUp) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.cd_navigate_up)
        )
    }
}

@Composable
fun HomeNavigationIcon(modifier: Modifier = Modifier) {
    Box(modifier.size(48.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_planeswalker_48_24_blue),
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun GatherAppBarPreview() {
    GatherTheme {
        TopAppBarWithBottomContent(
            title = { Text("Preview!") },
            navigationIcon = { NavigateUpIcon({}) }
        )
    }
}

@Preview
@Composable
fun GatherAppBarPreviewDark() {
    GatherTheme(darkTheme = true) {
        TopAppBarWithBottomContent(
            title = { Text("Preview!") },
            navigationIcon = { HomeNavigationIcon() }
        )
    }
}