package com.darkmoose117.gather.util

/**
 * A result encapsulation class inspired by [Result], but with less limitations.
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()

    fun getOrNull(): T? =
        when (this) {
            is Failure -> null
            is Success -> value
        }

    fun expectedErrorOrNull(): Exception? =
        when (this) {
            is Failure -> this.exception
            else -> null
        }
}

fun Exception.toFailure() = Result.Failure(this)