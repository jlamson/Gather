package com.darkmoose117.gather.ui.sets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.darkmoose117.gather.util.bottomBarPadding
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.ViewWindowInsetObserver
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@ExperimentalAnimatedInsets
class SetsFragment : Fragment() {

    private val viewModel: SetsViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
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
            val viewState by viewModel.viewState.observeAsState(SetsViewState.Loading)
            CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
                GatherTheme {
                    SetsScreen(
                        viewState,
                        onSetClicked = { setCode ->
                            findNavController().navigate(
                                R.id.cardsBySetFragment,
                                bundleOf("setCode" to setCode)
                            )
                        },
                        onToggleSort = { viewModel.toggleSort() },
                        onToggleAllTypes = { viewModel.toggleAllTypes() },
                        onToggleType = { viewModel.toggleType(it) },
                        modifier = Modifier.bottomBarPadding()
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSets()
    }
}
