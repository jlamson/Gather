package com.darkmoose117.gather.ui.sets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.GatherAppBar
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.util.ThemedPreview
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import org.joda.time.DateTime

@Composable
fun SetsScreen(
    viewState: SetsViewState,
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
                is SetsViewState.Success -> SetList(viewState.sets)
            }
        }

    }
}

@Composable
fun SetList(sets: List<MtgSet>) {
    LazyColumn {
        items(sets, { it.code }) { set ->
            SetItem(set)
        }
    }
}

@Composable
fun SetItem(set: MtgSet) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = set.code, Modifier.defaultMinSize(minWidth = 48.dp), style = MaterialTheme.typography.subtitle1)
        Text(set.name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.h5)
    }
}

val previewLoadingState = SetsViewState.Loading
val previewFailedState = SetsViewState.Failure(Throwable("Forced"))
// should show AAA, DDD, CCC, BBB
val previewSuccessState = SetsViewState.Success(
    listOf(
        MtgSet("AAA", "Set Named AAA", "", DateTime.now(), "AAA"),
        MtgSet("BBB", "Set Named BBB", "", DateTime.now().minusMonths(3), "BBB"),
        MtgSet("CCC", "Set Named CCC", "", DateTime.now().minusMonths(2), "CCC"),
        MtgSet("DDD", "Set Named DDD", "", DateTime.now().minusMonths(1), "DDD"),
    )
)

val testState = previewSuccessState

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun LightSetsScreen() {
    ThemedPreview { SetsScreen(testState) }
}

@Preview(widthDp = 360, heightDp = 480, showBackground = true)
@Composable
fun DarkSetsScreen() {
    ThemedPreview(darkTheme = true) { SetsScreen(testState) }
}