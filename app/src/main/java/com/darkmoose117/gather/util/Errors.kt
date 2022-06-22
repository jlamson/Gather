package com.darkmoose117.gather.util

class MissingArgException(
    arg: String,
    override val cause: Throwable? = null
): Throwable(
    message = "Missing SavedStateHandle arg: $arg",
    cause = cause
)