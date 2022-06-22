package com.darkmoose117.gather.ui.sets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.FabRevealedBottomSheetScaffold
import com.darkmoose117.gather.ui.components.HomeNavigationIcon
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.ui.components.ScrollToTopLazyColumn
import com.darkmoose117.gather.ui.components.StaggeredGrid
import com.darkmoose117.gather.ui.nav.Nav

@Composable
fun SetsScreen(
    navController: NavController
) {
    val viewModel: SetsViewModel = hiltViewModel()
    val viewState by viewModel.viewState.observeAsState(initial = SetsViewState.Loading)
    SetsContent(
        viewState = viewState,
        onSetClicked = { setCode ->
            navController.navigate(Nav.Dest.CardList.routeForSet(setCode))
        },
        onToggleSort = { viewModel.toggleSort() },
        onToggleAllTypes = { viewModel.toggleAllTypes() },
        onToggleType = { type -> viewModel.toggleType(type) }
    )
}

@Composable
fun SetsContent(
    viewState: SetsViewState,
    onSetClicked: (String) -> Unit,
    onToggleSort: () -> Unit,
    onToggleAllTypes: () -> Unit,
    onToggleType: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.sets)) },
            navigationIcon = { HomeNavigationIcon() }
        )

        when (viewState) {
            is SetsViewState.Loading -> LoadingCard()
            is SetsViewState.Failure -> ErrorCard(viewState.throwable.message ?: "Fuck.")
            is SetsViewState.Success -> SetList(viewState, onSetClicked, onToggleSort, onToggleAllTypes, onToggleType)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SetList(
    state: SetsViewState.Success,
    onSetClicked: (String) -> Unit,
    onToggleSort: () -> Unit,
    onToggleAllTypes: () -> Unit,
    onToggleType: (String) -> Unit
) {
    FabRevealedBottomSheetScaffold(
        sheetContent = {
            SetSortFilterBottomSheet(
                state = state,
                onToggleSort = onToggleSort,
                onToggleAllTypes = onToggleAllTypes,
                onToggleType = onToggleType
            )
        }
    ) {
        ScrollToTopLazyColumn(
            verticalSpacing = 4.dp
        ) {
            val grouped: Map<String, List<MagicSetRow>> = when (state.setsSortedBy) {
                SetsSortedBy.Name -> state.sets.groupBy {
                    val first = it.set.name.first().toString()
                    if (first.toIntOrNull() != null) "#" else first
                }
                SetsSortedBy.Date -> state.sets.groupBy { it.set.releasedAt?.substring(0..3) ?: "???"}
            }
            grouped.forEach { (groupLabel, sets) ->
                stickyHeader(groupLabel) {
                    SetGroupHeader(groupLabel)
                }

                items(items = sets, key = { it.set.code }) { row ->
                    SetItem(row, modifier = Modifier.clickable(onClick = { onSetClicked(row.set.code) }))
                }
            }
        }
    }
}

@Composable
private fun SetGroupHeader(groupLabel: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
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
fun SetItem(row: MagicSetRow, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = 48.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (depth, set) = row

        repeat (depth) { i ->
            val bg = when (i % 3) {
                0 -> Color.Magenta
                1 -> Color.Blue
                else -> Color.Green
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(24.dp)
                    .background(bg),
                content = { }
            )
        }

        Icon(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(set.iconSvgUri).build(),
                contentScale = ContentScale.Fit
            ),
            contentDescription = set.name,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .size(32.dp, 32.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(set.name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h5)
    }
}

@Composable
fun ColumnScope.SetSortFilterBottomSheet(
    state: SetsViewState.Success,
    onToggleSort: () -> Unit,
    onToggleAllTypes: () -> Unit,
    onToggleType: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleSort,
            enabled = state.setsSortedBy != SetsSortedBy.Name
        ) {
            Text(text = "Sort by Name")
        }
        Button(
            modifier = Modifier.weight(1f),
            onClick = onToggleSort,
            enabled = state.setsSortedBy != SetsSortedBy.Date
        ) {
            Text(text = "Sort by Date")
        }
    }

    StaggeredGrid(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Box(Modifier.padding(4.dp)) {
            AllTypeChip(
                enabled = state.typeDescriptors.all { it.shouldDisplay },
                onClick = onToggleAllTypes
            )
        }

        state.typeDescriptors.forEach { typeDescriptor ->
            Box(Modifier.padding(4.dp)) {
                TypeChip(typeDescriptor, onToggleType)
            }
        }
    }
}

@Composable
fun AllTypeChip(
    enabled: Boolean,
    onClick: () -> Unit
) {
    val chipShape = RoundedCornerShape(percent = 50)
    val backgroundColor by animateColorAsState(
        if (enabled) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    val iconTint by animateColorAsState(
        if (enabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(backgroundColor, chipShape)
            .border(2.dp, MaterialTheme.colors.primary, chipShape)
            .size(40.dp)
    ) {
        Icon(Icons.Outlined.FilterList,
            tint = iconTint,
            modifier = Modifier.size(32.dp),
            contentDescription = "Filter or Un-filter all types"
        )
    }
}

@Composable
fun TypeChip(
    typeDescriptor: TypeDescriptor,
    onChipClick: (String) -> Unit,
) {
    val chipShape = RoundedCornerShape(percent = 50)
    val backgroundColor by animateColorAsState(
        if (typeDescriptor.shouldDisplay) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    val textColor by animateColorAsState(
        if (typeDescriptor.shouldDisplay) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    )
    Surface(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colors.primary, chipShape)
            .defaultMinSize(minHeight = 40.dp),
        elevation = 2.dp,
        shape = chipShape,
        color = backgroundColor,
        contentColor = textColor
    ) {
        Box(modifier = Modifier.clickable(onClick = { onChipClick(typeDescriptor.type) })) {
            Text(
                text = "${typeDescriptor.type} (${typeDescriptor.count})",
                style = MaterialTheme.typography.button,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )
        }
    }
}
