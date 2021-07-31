package com.darkmoose117.gather.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.darkmoose117.gather.ui.theme.GatherTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import java.lang.IllegalArgumentException

@ExperimentalAnimatedInsets
class CardsBySetFragment : Fragment() {

    private val viewModel: CardsBySetViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CardsBySetViewModel::class.java)) {
                    return CardsBySetViewModel(arguments!!.getString("setCode")!!) as T
                } else throw IllegalArgumentException("Invalid modelClass: $modelClass")
            }
        }
    }

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
            val viewState by viewModel.viewState.observeAsState(viewModel.buildViewState())
            val lazyCardList = viewModel.cardsPager.flow.collectAsLazyPagingItems()
            CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
                GatherTheme {
                    CardListScreen(
                        lazyCardList,
                        viewState,
                        onToggleSort = { viewModel.toggleSort() },
                        onToggleViewType = { viewModel.toggleViewType() }
                    )
                }
            }
        }
    }
}