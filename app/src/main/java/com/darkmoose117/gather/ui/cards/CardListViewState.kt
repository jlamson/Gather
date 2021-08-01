package com.darkmoose117.gather.ui.cards

import androidx.compose.runtime.Immutable
import com.darkmoose117.scryfall.api.params.Order
import com.darkmoose117.scryfall.api.params.OrderString
import com.darkmoose117.scryfall.data.Card

@Immutable
data class CardListViewState(
    val query: String,
    val cardsSortedBy: CardsSortedBy,
    val cardsViewType: CardsViewType
)

@Immutable
enum class CardsSortedBy {
    Number {
        override fun order() = Order.SET
    },
    Name {
        override fun order() = Order.NAME
    };

    @OrderString
    abstract fun order(): String
}

@Immutable
enum class CardsViewType {
    Text, Image
}