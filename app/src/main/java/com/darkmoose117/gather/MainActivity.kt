package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.darkmoose117.gather.databinding.ContentMainBinding
import com.darkmoose117.gather.ui.components.GatherAppBar
import com.darkmoose117.gather.ui.components.GatherBottomBar
import com.darkmoose117.gather.ui.components.Tab
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

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
                var currentTab by remember { mutableStateOf(Tab.Sets) }

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
                                    Text(
                                        when (currentTab) {
                                            Tab.Sets -> "Sets"
                                            Tab.Search -> "Search"
                                        }
                                    )
                                },
                                actions = { }
                            )
                        },
                        bottomBar = {
                            GatherBottomBar(
                                currentTab = currentTab,
                                onSetsClicked = {
                                    currentTab = Tab.Sets
                                    findNavController().navigate(R.id.setsFragment)
                                },
                                onSearchClicked = {
                                    currentTab = Tab.Search
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
