package com.darkmoose117.gather.ui.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.darkmoose117.gather.R
import com.darkmoose117.gather.data.cards.CardRepository
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Failure
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Loading
import com.darkmoose117.gather.ui.cards.CardDetailViewState.Success
import com.darkmoose117.gather.ui.components.ErrorCard
import com.darkmoose117.gather.ui.components.LoadingCard
import com.darkmoose117.gather.ui.components.NavigateUpIcon
import com.darkmoose117.scryfall.data.Card

@Composable
fun CardDetailScreen(
    navController: NavController,
    repository: CardRepository,
    id: String
) {
    val viewModel: CardDetailViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CardDetailViewModel::class.java)) {
                return CardDetailViewModel(id, repository) as T
            } else throw IllegalArgumentException("Invalid modelClass $modelClass")
        }
    })
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
fun CardDetail(card: Card) {
    CardText(card = card)
}