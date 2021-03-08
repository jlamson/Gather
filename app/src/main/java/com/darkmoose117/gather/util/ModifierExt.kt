package com.darkmoose117.gather.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

fun Modifier.onLongPress(onLongPress: (Offset) -> Unit) = this.pointerInput(Unit) {
    detectTapGestures(onLongPress = onLongPress)
}

fun Modifier.bottomBarPadding() = this.padding(bottom = 64.dp)

// Currently applying extra padding for bottom nav, since this scaffold doesn't know
// about the bottom nav at the activity level.
fun Modifier.placeForFab() = this.navigationBarsPadding().bottomBarPadding()