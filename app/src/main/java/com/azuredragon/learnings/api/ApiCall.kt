package com.azuredragon.learnings.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber

fun <T> apiCallFlow(call: suspend () -> T): ApiFlow<T> {
    return flow<ApiResponse<T>> {
        emit(ApiResponse.InProgress)

        try {
            when (val result = call()) {
                is Flow<*> -> emit(ApiResponse.Error(Throwable("You should use specialized apiCallNestedFlow instead of apiCallFlow")))
                else -> {
                    Timber.d("apiCallFlow emitting: $result")
                    emit(ApiResponse.Success(result))
                }
            }
        } catch (t: Throwable) {
            emit(ApiResponse.Error(t))
        }
    }
}

fun <T> apiCallNestedFlow(call: suspend () -> Flow<T>): ApiFlow<T> {
    return flow<ApiResponse<T>> {
        emit(ApiResponse.InProgress)

        try {
            val result = call()
            result.catch {
                Timber.e("apiCallNestedFlow error: ${it.message}")

                emit(ApiResponse.Error(it, false))
            }.collect {
                Timber.d("apiCallNestedFlow emitting: $it")

                emit(ApiResponse.Success(it))
            }
        } catch (t: Throwable) {
            emit(ApiResponse.Error(t))
        }
    }
}