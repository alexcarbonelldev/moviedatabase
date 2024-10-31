package com.bd.data.remote

import com.bd.common.Either

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String, val code: Int? = null) : ApiResponse<T>()
}

fun <T, R> ApiResponse<T>.toEither(
    transformSuccess: (T) -> R
): Either<R> {
    return when (this) {
        is ApiResponse.Success -> Either.Right(transformSuccess(data))
        is ApiResponse.Error -> Either.Left(Exception(message))
    }
}
