package com.azuredragon.learnings.api

sealed class ApiResponse<out T> {
    object Default : ApiResponse<Nothing>()
    object InProgress : ApiResponse<Nothing>()
    data class Success<T>(val result: T) : ApiResponse<T>()
    data class Error<T>(val error: Throwable, val fromRoot: Boolean = true) : ApiResponse<T>()

    fun isInProgress() = this is InProgress
    fun isSuccess() = this is Success
    fun isError() = this is Error

    suspend fun handle(
        onProgress: () -> Unit,
        onSuccess: suspend (result: T) -> Unit,
        onError: (error: Throwable) -> Unit,
    ) {
        when (this) {
            is Default -> { /* no-op */
            }
            is InProgress -> onProgress()
            is Success -> onSuccess(this.result)
            is Error -> onError(this.error)
        }
    }

    fun onProgress(cb: () -> Unit): ApiResponse<T> {
        if (this is InProgress) {
            cb()
        }
        return this
    }

    suspend fun onSuccess(cb: suspend (result: T) -> Unit): ApiResponse<T> {
        if (this is Success) {
            cb(this.result)
        }
        return this
    }

    suspend fun onError(cb: suspend (error: Throwable) -> Unit): ApiResponse<T> {
        if (this is Error) {
            cb(this.error)
        }
        return this
    }
}