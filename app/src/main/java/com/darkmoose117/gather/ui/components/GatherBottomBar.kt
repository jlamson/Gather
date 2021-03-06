package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun GatherBottomBar(
    currentTab: Tab,
    onSetsClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    BottomNavigation(Modifier.navigationBarsPadding()) {
        BottomNavigationItem(
            selected = currentTab is Tab.Sets || currentTab is Tab.CardBySet,
            onClick = onSetsClicked,
            icon = {
                Icon(painterResource(id = R.drawable.ic_planewalker_24), contentDescription = null)
            },
            label = { Text("Sets", style = MaterialTheme.typography.caption) }
        )

        BottomNavigationItem(
            selected = currentTab == Tab.Search,
            onClick = onSearchClicked,
            icon = {
                Icon(Icons.Outlined.Search, contentDescription = null)
            },
            label = { Text("Search", style = MaterialTheme.typography.caption) }
        )
    }
}

@Immutable
sealed class Tab(val name: String) {
    object Sets: Tab("Sets")
    class CardBySet(setCode: String): Tab("Cards in $setCode")
    object Search: Tab("Search")
}


@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
fun DrawerPreview() {
    GatherTheme {
        Surface {
            Column {
                GatherBottomBar(Tab.Sets, {}, {})
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
                GatherBottomBar(Tab.Search, {}, {})
            }
        }
    }
}
