package com.darkmoose117.gather.ui.sets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.GatherAppBar
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.util.ThemedPreview
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.coroutines.launch
import org.joda.time.DateTime

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SetsScreen(
    viewState: SetsViewState,
    onToggleSort: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        GatherAppBar(
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            onNavIconPressed = { },
            title = { Text("Sets") },
            actions = { }
        )
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            when (viewState) {
                is SetsViewState.Loading -> LoadingCard()
                is SetsViewState.Failure -> ErrorCard(viewState.throwable.message ?: "Fuck.")
                is SetsViewState.Success -> SetList(viewState, onToggleSort)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SetList(state: SetsViewState.Success, onToggleSort: () -> Unit) {
    Box(modifier = Modifier.navigationBarsPadding()) {
        // Remember our own LazyListState
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 64.dp),
            state = listState
        ) {
            val grouped: Map<String, List<MtgSet>> = when (state.sortBy) {
                SortedBy.Name -> state.sets.groupBy {
                    val first = it.name.first().toString()
                    if (first.toIntOrNull() != null) "#" else first
                }
                SortedBy.Date -> state.sets.groupBy { it.releaseDate.year.toString() }
            }
            grouped.forEach { (groupLabel, sets) ->
                stickyHeader(groupLabel) {
                    SetGroupHeader(groupLabel)
                }

                items(sets, { it.code }) { set ->
                    SetItem(set)
                }
            }
        }

        val showButton = listState.firstVisibleItemIndex > 0

        AnimatedVisibility(
            visible = showButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Button(
                onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } },
            ) {
                Icon(Icons.Outlined.ArrowUpward, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.scroll_to_stop))
            }
        }

        FloatingActionButton(onClick = onToggleSort, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
        ) {
            Icon(Icons.Outlined.Sort, contentDescription = stringResource(R.string.toggle_sort))
        }
    }
}

@Composable
private fun SetGroupHeader(groupLabel: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text(
                text = groupLabel,
                style = MaterialTheme.typography.subtitle1
                    .copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun SetItem(set: MtgSet) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = set.code, Modifier.defaultMinSize(minWidth = 48.dp), style = MaterialTheme.typography.subtitle2)
        Text(set.name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h5)
    }
}

// region Previews

val previewLoadingState = SetsViewState.Loading
val previewFailedState = SetsViewState.Failure(Throwable("Forced"))
// should show AAA, DDD, CCC, BBB
val previewSuccessState = SetsViewState.Success(
    sets = mutableListOf<MtgSet>().apply {
        for (char in CharRange('A', 'Z')) {
            this.add(MtgSet(
                code = "$char$char$char",
                name = "Set named $char$char$char",
                type = "",
                releaseDate = DateTime.now(),
                block = "$char$char$char"
            ))
        }
    },
    sortBy = SortedBy.Name
)

val testState = previewSuccessState

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ThemedPreview { SetsScreen(testState, {}) }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ThemedPreview(darkTheme = true) { SetsScreen(testState, {}) }
}

// endregion
