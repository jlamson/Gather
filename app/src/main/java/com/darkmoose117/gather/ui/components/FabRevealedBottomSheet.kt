package com.darkmoose117.gather.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FabRevealedBottomSheetScaffold(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
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
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetContent = { sheetContent() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = toggleBottomSheet,
                modifier = Modifier.offset(y = -(16.dp + (FabSize / 2)))
            ) {
                Icon(Icons.Outlined.Sort, contentDescription = stringResource(R.string.filter_sort_fab))
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        content(it)
    }
}

private val FabSize = 56.dp