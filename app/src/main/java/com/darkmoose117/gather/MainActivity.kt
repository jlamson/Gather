package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.darkmoose117.gather.databinding.ContentMainBinding
import com.darkmoose117.gather.ui.components.GatherDrawer
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

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
                GatherTheme {
                    Scaffold(
                        scaffoldState = rememberScaffoldState(),
                        drawerContent = { GatherDrawer(
                            onSetsClicked = {
                                findNavController().navigate(R.id.setsFragment)
                            },
                            onSearchClicked = {
                                findNavController().navigate(R.id.searchFragment)
                            }
                        ) }
                    ) {
                        // TODO: Fragments inflated via AndroidViewBinding don't work as expected
                        //  https://issuetracker.google.com/179915946
                        // AndroidViewBinding(ContentMainBinding::inflate)
                        FragmentAwareAndroidViewBinding(ContentMainBinding::inflate)
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
