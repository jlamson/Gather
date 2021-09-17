package com.darkmoose117.gather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darkmoose117.gather.data.cards.CardRepository
import com.darkmoose117.gather.ui.cards.CardDetailScreen
import com.darkmoose117.gather.ui.cards.CardListScreen
import com.darkmoose117.gather.ui.nav.GatherBottomBar
import com.darkmoose117.gather.ui.nav.Nav
import com.darkmoose117.gather.ui.nav.Nav.Dest
import com.darkmoose117.gather.ui.search.SearchScreen
import com.darkmoose117.gather.ui.sets.SetsScreen
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.darkmoose117.scryfall.ScryfallApi
import com.darkmoose117.scryfall.data.CardSymbol

val LocalSymbolProvider = compositionLocalOf<Map<String, CardSymbol>> { emptyMap() }

@Composable
fun GatherUI(
    symbolMap: Map<String, CardSymbol>
) {
    // Drawer & Nav
    val cardRepository = remember { CardRepository(ScryfallApi.cardsApi) }
    GatherTheme {
        CompositionLocalProvider(
            LocalSymbolProvider provides symbolMap
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { GatherBottomBar(navController) }
            ) { scaffoldPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Dest.Sets.route,
                    modifier = Modifier.padding(scaffoldPadding)
                ) {
                    composable(Dest.Sets.route) {
                        SetsScreen(navController)
                    }

                    composable(Dest.Search.route) {
                        SearchScreen(navController)
                    }

                    composable(Dest.CardList.route) { backStackEntry ->
                        CardListScreen(
                            navController = navController,
                            repository = cardRepository,
                            query = backStackEntry.arguments?.getString(Nav.Args.QUERY)!!
                        )
                    }

                    composable(Dest.CardDetail.route) { backStackEntry ->
                        CardDetailScreen(
                            navController = navController,
                            repository = cardRepository,
                            id = backStackEntry.arguments?.getString(Nav.Args.ID)!!
                        )
                    }
                }
            }
        }
    }
}