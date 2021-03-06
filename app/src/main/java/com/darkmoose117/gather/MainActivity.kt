package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.darkmoose117.gather.databinding.ContentMainBinding
import com.darkmoose117.gather.ui.components.GatherAppBar
import com.darkmoose117.gather.ui.components.GatherBottomBar
import com.darkmoose117.gather.ui.components.Tab
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Provide WindowInsets to our content. We don't want to consume them, so that
            // they keep being pass down the view hierarchy (since we're using fragments).
            ProvideWindowInsets(consumeWindowInsets = false) {
                val scope = rememberCoroutineScope()
                var currentTab by remember { mutableStateOf<Tab>(Tab.Sets) }

                // Launched once, and only re-run when the key changes (Unit should never change.
                LaunchedEffect(key1 = Unit) {
                    findNavController().addOnDestinationChangedListener { _, destination, arguments ->
                        // Make sure you are using a safe context to change
                        scope.launch {
                            currentTab = when (destination.id) {
                                R.id.setsFragment -> Tab.Sets
                                R.id.cardsBySetFragment -> Tab.CardBySet(arguments?.getString("setCode") ?: "???")
                                R.id.searchFragment -> Tab.Search
                                else -> throw IllegalStateException("Add missing Tab for destination: $destination")
                            }
                        }
                    }
                }

                // Drawer & Nav
                GatherTheme {
                    Scaffold(
                        topBar = {
                            GatherAppBar(
                                // Use statusBarsPadding() to move the app bar content below the status bar
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .statusBarsPadding(),
                                onNavIconPressed = { },
                                title = {
                                    Text(currentTab.name)
                                },
                                actions = { }
                            )
                        },
                        bottomBar = {
                            GatherBottomBar(
                                currentTab = currentTab,
                                onSetsClicked = {
                                    findNavController().navigate(R.id.setsFragment)
                                },
                                onSearchClicked = {
                                    findNavController().navigate(R.id.searchFragment)
                                }
                            )
                        }
                    ) {
                        // TODO: Fragments inflated via AndroidViewBinding don't work as expected
                        //  https://issuetracker.google.com/179915946
                        // AndroidViewBinding(ContentMainBinding::inflate)
                        FragmentAwareAndroidViewBinding(ContentMainBinding::inflate, Modifier.navigationBarsPadding())
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}
