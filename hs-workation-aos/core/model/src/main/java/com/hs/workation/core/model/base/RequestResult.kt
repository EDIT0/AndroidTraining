package com.hs.workation.core.model.base

sealed class RequestResult<T>(
    val resultData: T? = null,
    val code: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : RequestResult<T>(data)
    class Error<T>(code: Int, message: String?) : RequestResult<T>(code = code, message = message)
    class ConnectionError<T>(code: Int, message: String?) : RequestResult<T>(code = code, message = message)
    class DataEmpty<T>() : RequestResult<T>()
}