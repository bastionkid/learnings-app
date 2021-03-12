package com.azuredragon.learnings.ktxextensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

fun <T> CoroutineScope.launchWithTimeout(
    timeoutInMillis: Long,
    suspendOperation: suspend () -> T,
    onSuccess: (T) -> Unit,
    onTimeOut: () -> Unit
) {
    val operation = launch {
        val operationResult = suspendOperation.invoke()
        onSuccess(operationResult)
    }

    launch {
        delay(timeoutInMillis)

        if (!operation.isCompleted) {
            Timber.d("CoroutineScope.launchWithTimeout: $suspendOperation cancelled after $timeoutInMillis millis")
            operation.cancel()

            onTimeOut.invoke()
        }
    }
}