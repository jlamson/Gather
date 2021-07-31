package com.darkmoose117.gather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darkmoose117.gather.ui.cards.CardListScreen
import com.darkmoose117.gather.ui.nav.GatherBottomBar
import com.darkmoose117.gather.ui.nav.Nav
import com.darkmoose117.gather.ui.nav.Nav.Dest
import com.darkmoose117.gather.ui.search.SearchScreen
import com.darkmoose117.gather.ui.sets.SetsScreen
import com.darkmoose117.gather.ui.theme.GatherTheme

@Composable
fun GatherUI() {
    // Drawer & Nav
    GatherTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { GatherBottomBar(navController) }
        ) { scaffoldPadding ->
            NavHost(
                navController = navController,
                startDestination = Dest.Sets.route,
                modifier = Modifier.padding(scaffoldPadding)
            ) {
                composable(Dest.Sets.route) { SetsScreen(navController) }
                composable(Dest.Search.route) { SearchScreen(navController) }
                composable(Dest.CardList.route) { backStackEntry ->
                    CardListScreen(
                        navController = navController,
                        query = backStackEntry.arguments?.getString(Nav.Args.QUERY)!!
                    )
                }
            }
        }
    }
}