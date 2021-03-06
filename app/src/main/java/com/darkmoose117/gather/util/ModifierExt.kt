package com.darkmoose117.gather.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.onLongPress(onLongPress: (Offset) -> Unit) = this.pointerInput(Unit) {
    detectTapGestures(onLongPress = onLongPress)
}