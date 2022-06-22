package com.darkmoose117.gather.ui.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.darkmoose117.gather.R
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Failure
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Loading
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Success
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.ui.components.NavigateUpIcon
import com.darkmoose117.scryfall.data.Card

const val ART_CROP_ASPECT = 4f / 3f

@Composable
fun CardDetailScreen(
    navController: NavController
) {
    val viewModel: CardDetailViewModel = hiltViewModel()
    val viewState by viewModel.viewState.observeAsState(Loading)

    CardDetailContent(
        viewState = viewState,
        navigateUp = { navController.popBackStack() }
    )
}

@Composable
fun CardDetailContent(
    viewState: CardDetailViewState,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        var titleText by remember { mutableStateOf("") }
        TopAppBar(
            title = { Text(titleText) },
            navigationIcon = { NavigateUpIcon(navigateUp) }
        )

        when (viewState) {
            is Success -> {
                titleText = viewState.card.name
                CardDetail(card = viewState.card)
            }
            is Loading -> LoadingCard()
            is Failure -> {
                ErrorCard(
                    errorMessage = viewState.throwable.localizedMessage
                        ?: stringResource(id = R.string.generic_error)
                )
            }
        }
    }
}

@Composable
fun CardDetail(card: Card) = Column {
    card.imageUris?.artCrop?.let { artCropUri ->
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artCropUri)
                .crossfade(true)
                .build(),
            contentDescription = card.name,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ART_CROP_ASPECT)
        )
    }
    Spacer(modifier = Modifier
        .height(16.dp)
        .fillMaxWidth())
    CardText(card = card)
}