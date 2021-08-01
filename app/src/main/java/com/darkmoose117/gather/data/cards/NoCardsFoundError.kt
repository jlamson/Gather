package com.darkmoose117.gather.data.cards

class NoCardsFoundError(query: String, cause: Exception? = null) : Exception(
    "No Cards found for query: \"$query\"",
    cause
)