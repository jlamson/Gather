package com.darkmoose117.gather.ui.cards

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.FabRevealedBottomSheetScaffold
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.ui.components.NavigateUpIcon
import com.darkmoose117.gather.ui.nav.Nav
import com.darkmoose117.scryfall.data.Card
import kotlin.math.roundToInt

@Composable
fun CardListScreen(
    navController: NavController
) {
    val viewModel: CardListViewModel = hiltViewModel()
    val lazyCardList = viewModel.cardsPager.flow.collectAsLazyPagingItems()
    val viewState by viewModel.viewState.observeAsState(viewModel.buildViewState())

    CardListContent(
        cardList = lazyCardList,
        viewState = viewState,
        navigateUp = { navController.popBackStack() },
        onCardTapped = { card -> navController.navigate(Nav.Dest.CardDetail.route(card.id)) },
        onToggleSort = { viewModel.toggleSort() },
        onToggleViewType = { viewModel.toggleViewType() }
    )
}

@Composable
fun CardListContent(
    cardList: LazyPagingItems<Card>,
    viewState: CardListViewState,
    navigateUp: () -> Unit,
    onCardTapped: (Card) -> Unit,
    onToggleSort: () -> Unit,
    onToggleViewType: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(viewState.query) },
            navigationIcon = { NavigateUpIcon(navigateUp) }
        )

        CardList(cardList, viewState, onCardTapped, onToggleSort, onToggleViewType)
    }
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun CardList(
    lazyCards: LazyPagingItems<Card>,
    viewState: CardListViewState,
    onCardTapped: (Card) -> Unit,
    onToggleSort: () -> Unit,
    onToggleViewType: () -> Unit
) {
    FabRevealedBottomSheetScaffold(
        sheetContent = {
            CardSortFilterBottomSheet(
                sortedBy = viewState.cardsSortedBy,
                cardsViewType = viewState.cardsViewType,
                onToggleSort = onToggleSort,
                onToggleViewType = onToggleViewType
            )
        }
    ) {

        when (val state = lazyCards.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingCard(modifier = Modifier.fillMaxWidth())
            }
            is LoadState.Error -> {
                ErrorCard(
                    modifier = Modifier.fillMaxWidth(),
                    errorMessage = state.error.localizedMessage!!
                )
            }
            else -> Unit
        }

        PinchToZoomLazyGrid(viewType = viewState.cardsViewType) { itemSpacing ->
            items(count = lazyCards.itemCount) { index ->
                val card = lazyCards[index]!!
                CardListItem(
                    card = card,
                    viewType = viewState.cardsViewType,
                    modifier = Modifier
                        .padding(bottom = itemSpacing, end = itemSpacing)
                        .clickable(
                            indication = rememberRipple(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onCardTapped(card) },
                        )
                )
            }

            lazyCards.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                Modifier.size(48.dp)
                            )
                        }
                    }
                    is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item {
                            ErrorCard(
                                errorMessage = e.error.localizedMessage!!
                            )
                        }
                    }
                    is LoadState.NotLoading -> Unit
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinchToZoomLazyGrid(
    viewType: CardsViewType,
    modifier: Modifier = Modifier,
    lazyContent: LazyGridScope.(itemSpacing: Dp) -> Unit
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    var scale by remember(viewType, isPortrait) {
        mutableStateOf(
            when (viewType) {
                CardsViewType.Text -> 1f
                CardsViewType.Image -> 2f
            }
        )
    }
    val columnCount by remember(scale) {
        derivedStateOf { scale.roundToInt() }
    }
    val itemSpacing by remember(columnCount) {
        derivedStateOf {
            when (columnCount) {
                1 -> 16.dp
                2 -> 8.dp
                else -> 4.dp
            }
        }
    }
    var cells by remember(columnCount) { mutableStateOf(GridCells.Fixed(columnCount)) }
    val columnRange = remember(viewType, isPortrait) {
        columnRange(viewType, isPortrait)
    }
    val state = rememberTransformableState { zoomChange, _/*panChange*/, _/*rotationChange*/ ->
        scale = (scale * (1 / zoomChange)).coerceIn(columnRange)
        cells = GridCells.Fixed(columnCount)
    }
    Box(
        modifier = modifier.transformable(state = state)
    ) {
        LazyVerticalGrid(columns = cells, contentPadding = PaddingValues(itemSpacing)) {
            lazyContent(itemSpacing)
        }
    }
}

fun columnRange(viewType: CardsViewType, isPortrait: Boolean): ClosedFloatingPointRange<Float> {
    var min = 1f
    var max = 1f
    when (viewType) {
        CardsViewType.Text -> {
            min = 1f
            max = 1f // TODO make better when I can stagger
        }
        CardsViewType.Image -> {
            if (isPortrait) {
                min = 1f
                max = 3f
            } else {
                min = 3f
                max = 5f
            }
        }
    }

    return min..max
}