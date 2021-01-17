package com.azuredragon.learnings.time

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

object Clock {
    private const val workName = "clock_sync"

    //skew in milliseconds =>> serverTime - deviceTime
    private var skewMillis = 0L
//    private var isStable = false

    val everySecond by lazy { Tick(TimeUnit.SECONDS) }
    val everyMinute by lazy { Tick(TimeUnit.MINUTES) }
    val everyHour by lazy { Tick(TimeUnit.HOURS) }
    val everyDay by lazy { Tick(TimeUnit.DAYS) }

    fun now(): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = currentTimeMillis()
        }
    }

    fun currentTimeMillis(): Long {
        return System.currentTimeMillis() + skewMillis
    }


    fun sync(lifecycleOwner: LifecycleOwner, context: Context) {
        lifecycleOwner.lifecycle.addObserver(TimeSetReceiver(context))

        synchronize(context)
        scheduleSync(context)
    }

    internal fun synchronize(context: Context) {
        val wm = WorkManager.getInstance(context)

        //begin sync now, NOT cancelling any ongoing syncs
        val oneTimeRequest = OneTimeWorkRequestBuilder<ClockSyncWorker>().build()
        wm.enqueueUniqueWork("$workName-once", ExistingWorkPolicy.KEEP, oneTimeRequest)
    }

    private fun scheduleSync(context: Context) {
        val wm = WorkManager.getInstance(context)

        //start periodic sync every 6 hours, after initial delay of 1 hour
        val periodicWork = PeriodicWorkRequestBuilder<ClockSyncWorker>(6, TimeUnit.HOURS)
            .setInitialDelay(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder().setRequiresBatteryNotLow(true).build()
            ).build()

        wm.enqueueUniquePeriodicWork(workName, ExistingPeriodicWorkPolicy.KEEP, periodicWork)
    }

    internal fun updateSkew(skewMillis: Long) {
        Timber.d(TAG, "updating skew to $skewMillis")
        Clock.skewMillis = skewMillis
    }

    private val TAG = Clock::class.java.canonicalName
}