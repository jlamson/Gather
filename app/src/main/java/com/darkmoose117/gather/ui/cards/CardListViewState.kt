package com.darkmoose117.gather.ui.cards

import androidx.compose.runtime.Immutable
import com.darkmoose117.scryfall.data.Card

@Immutable
sealed class CardListViewState {
    object Loading : CardListViewState()
    data class Success(
        val cards: List<Card>,
        val cardsSortedBy: CardsSortedBy,
        val cardsViewType: CardsViewType
    ) : CardListViewState()
    class Failure(
        val throwable: Throwable
    ) : CardListViewState()
}

@Immutable
enum class CardsSortedBy {
    Number, Name
}

@Immutable
enum class CardsViewType {
    Text, Image
}