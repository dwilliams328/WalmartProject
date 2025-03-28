package com.example.walmartcodingassessment.data.service

/**
 * Result of api transaction
 */
sealed class ApiResult<out T> {
    open val data: T? = null
    open val error: Throwable? = null

    data object Uninitialized: ApiResult<Nothing>()
    data object Loading: ApiResult<Nothing>()
    data class Success<T>(override val data: T): ApiResult<T>()
    data class Failure<out T>(override val error: Throwable): ApiResult<T>()
}
