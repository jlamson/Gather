package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
fun ColumnScope.GatherDrawer(
    onSetsClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    // Use statusBarsHeight() to add a spacer which pushes the drawer content
    // below the status bar (y-axis)
    Spacer(Modifier.statusBarsHeight())
    DrawerHeader()
    Divider()
    NavOption(text = "Sets", onClicked = onSetsClicked)
    Divider()
    NavOption(text = "Search", onClicked = onSearchClicked)
}

@Composable
private fun DrawerHeader() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text("Gather", style = MaterialTheme.typography.h2, modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun NavOption(text: String, onClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClicked),
        verticalAlignment = CenterVertically
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text, style = MaterialTheme.typography.body2, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
fun DrawerPreview() {
    GatherTheme {
        Surface {
            Column {
                GatherDrawer({}, {})
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
fun DrawerPreviewDark() {
    GatherTheme(darkTheme = true) {
        Surface {
            Column {
                GatherDrawer({}, {})
            }
        }
    }
}
