package com.azuredragon.learnings.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlin.random.Random

class FlowTestViewModel: ViewModel() {

    @ExperimentalCoroutinesApi
    private val randomFlow: Flow<Int> = callbackFlow {
        while (isActive) {
            delay(1000)

            offer(Random.nextInt())
        }

        awaitClose()
    }

    @ExperimentalCoroutinesApi
    val randomLiveData: LiveData<Int> = randomFlow.asLiveData()

}