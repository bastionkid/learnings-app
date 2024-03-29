package com.azuredragon.learnings.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.azuredragon.learnings.api.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import timber.log.Timber
import kotlin.random.Random

class FlowTestViewModel: ViewModel() {

    @ExperimentalCoroutinesApi
    private val randomFlow: Flow<Int> = callbackFlow {
        while (isActive) {
            delay(1000)

            val offeredValue = Random.nextInt()

            Timber.d("randomFlow offeredValue: $offeredValue")

            trySend(offeredValue)
        }

        awaitClose()
    }

    @ExperimentalCoroutinesApi
    val randomLiveData: LiveData<Int> = randomFlow.asLiveData()

    private val retryFlow: Flow<Unit> = flow {
        for (i in 1..10) {
            Timber.d("retryFlow i: $i")
            delay(1000)

            if (i % 3 == 0) {
                throw IllegalStateException()
            } else {
                emit(Unit)
            }
        }
    }.retryWhen { cause, attempt ->
        Timber.d("retry attempt: $attempt")

        return@retryWhen cause is IllegalStateException && attempt < 3
    }.catch {
        Timber.d("Closing retryFlow after 3 retries")
    }

    val retryLiveData: LiveData<Unit> = retryFlow.asLiveData()

}