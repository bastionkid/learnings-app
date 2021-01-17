package com.azuredragon.learnings.time

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

internal class ClockSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return Result.success()
    }
}