package com.azuredragon.learnings.time

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class Tick internal constructor(private val unit: TimeUnit) : LiveData<Long>() {

    override fun onActive() {
        scope.launch {
            while (isActive) {
                val interval = msTillNextTick()
                delay(interval)
                this@Tick.value = Clock.currentTimeMillis()
            }
        }
    }

    private fun msTillNextTick(): Long {
        val nextTick = Clock.now()

        when (unit) {
            TimeUnit.NANOSECONDS -> {
                throw Throwable("Nano tick not supported")
            }
            TimeUnit.MICROSECONDS -> {
                throw Throwable("Micro tick not supported")
            }
            TimeUnit.MILLISECONDS -> {
                throw Throwable("Milli tick not supported")
            }
            TimeUnit.SECONDS -> {
                nextTick.add(Calendar.SECOND, 1)
            }
            TimeUnit.MINUTES -> {
                nextTick.add(Calendar.MINUTE, 1)
                nextTick.set(Calendar.SECOND, 0)
            }
            TimeUnit.HOURS -> {
                nextTick.add(Calendar.HOUR_OF_DAY, 1)
                nextTick.set(Calendar.MINUTE, 0)
                nextTick.set(Calendar.SECOND, 0)
            }
            TimeUnit.DAYS -> {
                nextTick.add(Calendar.DATE, 1)
                nextTick.set(Calendar.HOUR_OF_DAY, 0)
                nextTick.set(Calendar.MINUTE, 0)
                nextTick.set(Calendar.SECOND, 0)
            }
        }

        nextTick.set(Calendar.MILLISECOND, 0)

        return nextTick.timeInMillis - Clock.currentTimeMillis()
    }

    override fun onInactive() {
        scope.job.cancelChildren()
    }

    private val scope = object : CoroutineScope {
        val job: Job = SupervisorJob()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job
    }
}