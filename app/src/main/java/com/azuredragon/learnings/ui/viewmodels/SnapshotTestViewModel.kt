package com.azuredragon.learnings.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SnapshotTestViewModel: ViewModel() {

    val uiState = mutableStateOf(false).apply {
        Timber.d("uiState created. Snapshot.current = ${Snapshot.current}, Thread = ${Thread.currentThread()}")
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("updating uiState before delay. Snapshot.current = ${Snapshot.current}, Thread = ${Thread.currentThread()}")
            delay(10)
            Timber.d("updating uiState after delay. Snapshot.current = ${Snapshot.current}, Thread = ${Thread.currentThread()}")
            uiState.value = true
            Timber.d("uiState updated. Snapshot.current = ${Snapshot.current}, Thread = ${Thread.currentThread()}")
        }
    }
}