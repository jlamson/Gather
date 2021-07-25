package com.darkmoose117.gather.ui.sets

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.ui.components.ScrollToTopLazyColumn
import com.darkmoose117.gather.ui.components.StaggeredGrid
import com.darkmoose117.gather.util.ThemedPreview
import com.darkmoose117.gather.util.placeForFab
import com.darkmoose117.scryfall.data.MagicSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SetsScreen(
    viewState: SetsViewState,
    onSetClicked: (String) -> Unit,
    onToggleSort: () -> Unit,
    onToggleAllTypes: () -> Unit,
    onToggleType: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            when (viewState) {
                is SetsViewState.Loading -> LoadingCard()
                is SetsViewState.Failure -> ErrorCard(viewState.throwable.message ?: "Fuck.")
                is SetsViewState.Success -> SetList(viewState, onSetClicked, onToggleSort, onToggleAllTypes, onToggleType)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SetList(
    state: SetsViewState.Success,
    onSetClicked: (String) -> Unit,
    onToggleSort: () -> Unit,
    onToggleAllTypes: () -> Unit,
    onToggleType: (String) -> Unit
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = toggleBottomSheet,
                modifier = Modifier.placeForFab()
            ) {
                Icon(Icons.Outlined.Sort, contentDescription = stringResource(R.string.filter_sort_fab))
            }
        },
        sheetContent = {
            SetSortFilterBottomSheet(
                state = state,
                onToggleSort = onToggleSort,
                onToggleAllTypes = onToggleAllTypes,
                onToggleType = onToggleType
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        ScrollToTopLazyColumn {
            val grouped: Map<String, List<MagicSet>> = when (state.setsSortedBy) {
                SetsSortedBy.Name -> state.sets.groupBy {
                    val first = it.name.first().toString()
                    if (first.toIntOrNull() != null) "#" else first
                }
                SetsSortedBy.Date -> state.sets.groupBy { it.releasedAt.substring(0..3) }
            }
            grouped.forEach { (groupLabel, sets) ->
                stickyHeader(groupLabel) {
                    SetGroupHeader(groupLabel)
                }

                items(items = sets, key = { it.code }) { set ->
                    SetItem(set, modifier = Modifier.clickable(onClick = { onSetClicked(set.code) }))
                }
            }
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
fun SetItem(set: MagicSet, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = set.code, Modifier.defaultMinSize(minWidth = 48.dp), style = MaterialTheme.typography.subtitle2)
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


// region Previews

val previewLoadingState = SetsViewState.Loading
val previewFailedState = SetsViewState.Failure(Throwable("Forced"))
// should show AAA, DDD, CCC, BBB
val previewSuccessState = SetsViewState.Success(
    sets = mutableListOf<MagicSet>().apply {
        for (char in listOf('A', 'A', 'A', 'B', 'B', 'C', 'C', 'C', 'C', 'C', 'C', 'C', 'C')) {
            this.add(MagicSet(
                code = "$char$char$char",
                name = "$char$char$char Set name",
                setType = "Promo",
                releasedAt = "2021-02-25"
            ))
        }
    },
    listOf(
        TypeDescriptor("FilterOn", 10,true),
        TypeDescriptor("FilterOff", 8, false),
        TypeDescriptor("FilterA", 18894, true),
        TypeDescriptor("FilterB", 1, false),
        TypeDescriptor("FilterC", 17, true),
        TypeDescriptor("FilterD", 22, false),
        TypeDescriptor("FilterOn", 10,false),
        TypeDescriptor("FilterOff", 8, true),
        TypeDescriptor("FilterA", 18894, false),
        TypeDescriptor("FilterB", 1, true),
        TypeDescriptor("FilterC", 17, false),
        TypeDescriptor("FilterD", 22, true),
    ),
    setsSortedBy = SetsSortedBy.Name
)

val testState = previewSuccessState

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun BottomSheet() {
    ThemedPreview(darkTheme = true) {
        Column {
            SetSortFilterBottomSheet(previewSuccessState, {}, {}, {})
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ThemedPreview { SetsScreen(testState, {}, {}, {}, {}) }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ThemedPreview(darkTheme = true) { SetsScreen(testState, {}, {}, {}, {}) }
}

// endregion
