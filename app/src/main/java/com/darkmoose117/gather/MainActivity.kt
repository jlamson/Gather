package com.darkmoose117.gather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.darkmoose117.gather.databinding.ContentMainBinding
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.darkmoose117.gather.util.BackPressHandler
import com.darkmoose117.gather.util.LocalBackPressedDispatcher
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Provide WindowInsets to our content. We don't want to consume them, so that
            // they keep being pass down the view hierarchy (since we're using fragments).
            ProvideWindowInsets(consumeWindowInsets = false) {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides this.onBackPressedDispatcher
                ) {
                    val scaffoldState = rememberScaffoldState()

                    val scope = rememberCoroutineScope()

                    val openDrawerEvent = viewModel.drawerShouldBeOpened.observeAsState()
                    if (openDrawerEvent.value == true) {
                        // Open drawer and reset state in VM.
                        scope.launch {
                            scaffoldState.drawerState.open()
                            viewModel.resetOpenDrawerAction()
                        }
                    }

                    // Intercepts back navigation when the drawer is open
                    if (scaffoldState.drawerState.isOpen) {
                        BackPressHandler {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                    }

                    // Drawer & Nav
                    GatherTheme {
                        Scaffold(
                            scaffoldState = scaffoldState,
//                            drawerContent = {
//                                GatherDrawer(
//                                    onSetsClicked = {
//                                        findNavController().navigate(R.id.setsFragment)
//                                        scope.launch { scaffoldState.drawerState.close() }
//                                    },
//                                    onSearchClicked = {
//                                        findNavController().navigate(R.id.searchFragment)
//                                        scope.launch { scaffoldState.drawerState.close() }
//                                    }
//                                )
//                            }
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
