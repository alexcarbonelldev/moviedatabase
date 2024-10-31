package com.bd.data.remote

import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse.Success(apiCall())
    } catch (e: HttpException) {
        ApiResponse.Error(
            message = e.message.orEmpty(),
            code = e.code()
        )
    } catch (e: Exception) {
        ApiResponse.Error(message = e.message.orEmpty())
    }
}
