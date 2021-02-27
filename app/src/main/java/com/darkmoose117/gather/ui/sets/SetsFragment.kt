package com.darkmoose117.gather.ui.sets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import com.darkmoose117.gather.ui.theme.GatherTheme
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.ViewWindowInsetObserver

@ExperimentalAnimatedInsets
class SetsFragment : Fragment() {

    private lateinit var viewModel: SetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)


        // Create a ViewWindowInsetObserver using this view, and call start() to
        // start listening now. The WindowInsets instance is returned, allowing us to
        // provide it to AmbientWindowInsets in our content below.
        val windowInsets = ViewWindowInsetObserver(this)
            // We use the `windowInsetsAnimationsEnabled` parameter to enable animated
            // insets support. This allows our `ConversationContent` to animate with the
            // on-screen keyboard (IME) as it enters/exits the screen.
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
                GatherTheme {
                    SetsScreen()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetsViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
