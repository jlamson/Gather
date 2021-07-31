package com.darkmoose117.gather.util

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.darkmoose117.gather.ui.theme.GatherTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    GatherTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}