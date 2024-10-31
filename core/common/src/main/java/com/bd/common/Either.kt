package com.bd.common

sealed class Either<T> {
    data class Right<T>(val value: T) : Either<T>()
    data class Left<T>(val error: Throwable) : Either<T>()
}

fun <T> Either<T>.onSuccess(block: (T) -> Unit): Either<T> {
    if (this is Either.Right) {
        block(value)
    }
    return this
}

fun <T> Either<T>.onFailure(block: (Throwable) -> Unit): Either<T> {
    if (this is Either.Left) {
        block(error)
    }
    return this
}
