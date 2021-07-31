package com.darkmoose117.gather.ui.nav

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.nav.Nav.Dest
import com.darkmoose117.gather.ui.theme.GatherTheme

@Composable
fun GatherBottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        BottomNavigationItem(
            selected = currentDestination.isDestInHierarchy(Dest.Sets),
            onClick = { navController.toRootDest(Dest.Sets) },
            icon = {
                Icon(painterResource(id = R.drawable.ic_planewalker_24), contentDescription = null)
            },
            label = { Text(stringResource(id = R.string.sets)) },
        )

        BottomNavigationItem(
            selected = currentDestination.isDestInHierarchy(Dest.Search),
            onClick = { navController.toRootDest(Dest.Search) },
            icon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            label = { Text(stringResource(id = R.string.search)) }
        )
    }
}

fun NavDestination?.isDestInHierarchy(dest: Dest) = this?.hierarchy?.any {
    it.route == dest.route
} == true

fun NavController.toRootDest(dest: Dest) = navigate(dest.route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }
    // Avoid multiple copies of the same destination when
    // re-selecting the same item
    launchSingleTop = true
    // Restore state when re-selecting a previously selected item
    restoreState = true
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
fun DrawerPreview() {
    GatherTheme {
        GatherBottomBar(rememberNavController())
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
fun DrawerPreviewDark() {
    GatherTheme(darkTheme = true) {
        GatherBottomBar(rememberNavController())
    }
}
