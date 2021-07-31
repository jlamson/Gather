package com.darkmoose117.gather.ui.cards

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.components.CardListItem
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.util.bottomBarPadding
import com.darkmoose117.gather.util.placeForFab
import com.darkmoose117.scryfall.data.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CardListScreen(
    cardList: LazyPagingItems<Card>,
    viewState: CardListViewState,
    onToggleSort: () -> Unit,
    onToggleViewType: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            CardList(cardList, viewState, onToggleSort, onToggleViewType)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun CardList(
    lazyCards: LazyPagingItems<Card>,
    viewState: CardListViewState,
    onToggleSort: () -> Unit,
    onToggleViewType: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val toggleBottomSheet: () -> Unit = {
        scope.launch(Dispatchers.Main) {
            if (scaffoldState.bottomSheetState.isExpanded) {
                scaffoldState.bottomSheetState.collapse()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.bottomBarPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = toggleBottomSheet,
                modifier = Modifier.placeForFab()
            ) {
                Icon(Icons.Outlined.Sort, contentDescription = stringResource(R.string.filter_sort_fab))
            }
        },
        sheetContent = {
            CardSortFilterBottomSheet(
                sortedBy = viewState.cardsSortedBy,
                cardsViewType = viewState.cardsViewType,
                onToggleSort = onToggleSort,
                onToggleViewType = onToggleViewType
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        var scale by remember { mutableStateOf(1f) }
        var cells by remember { mutableStateOf(GridCells.Fixed(scale.roundToInt())) }
        val columnMax = when (LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 5f
            else -> 3f
        }
        val state = rememberTransformableState { zoomChange, _/*panChange*/, _/*rotationChange*/ ->
            scale = (scale * (1 / zoomChange)).coerceIn(1f, columnMax)
            cells = GridCells.Fixed(scale.roundToInt())
        }
        Box(
            modifier = Modifier.transformable(state = state)
        ) {
            val itemSpacing = when (cells.count) {
                1 -> 16.dp
                2 -> 8.dp
                else -> 4.dp
            }
            LazyVerticalGrid(
                cells = cells,
                contentPadding = PaddingValues(top = itemSpacing, start = itemSpacing)
            ) {
                items(count = lazyCards.itemCount) { index ->
                    CardListItem(
                        card = lazyCards[index]!!,
                        viewType = viewState.cardsViewType,
                        modifier = Modifier.padding(bottom = itemSpacing, end = itemSpacing)
                    )
                }

                lazyCards.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingCard(modifier = Modifier.fillParentMaxSize())
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    Modifier.size(48.dp).align(Alignment.Center)
                                )
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = loadState.refresh as LoadState.Error
                            item {
                                ErrorCard(
                                    modifier = Modifier.fillParentMaxSize(),
                                    errorMessage = e.error.localizedMessage!!
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = loadState.append as LoadState.Error
                            item {
                                ErrorCard(
                                    errorMessage = e.error.localizedMessage!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.CardSortFilterBottomSheet(
    sortedBy: CardsSortedBy,
    cardsViewType: CardsViewType,
    onToggleSort: () -> Unit,
    onToggleViewType: () -> Unit
) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleSort,
            enabled = sortedBy != CardsSortedBy.Name
        ) {
            Text(text = "Sort by Name")
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleSort,
            enabled = sortedBy != CardsSortedBy.Number
        ) {
            Text(text = "Sort by Number")
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleViewType,
            enabled = cardsViewType != CardsViewType.Image
        ) {
            Text(text = "View Images")
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleViewType,
            enabled = cardsViewType != CardsViewType.Text
        ) {
            Text(text = "View Text")
        }
    }
}