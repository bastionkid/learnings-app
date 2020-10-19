package com.azuredragon.learnings.ui.utils

import com.azuredragon.learnings.ktxextensions.killAndExitProcessWithCleanStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

class UncaughtExceptionHandler(private val block: () -> Unit): Thread.UncaughtExceptionHandler {

    private val exceptionCounter = AtomicInteger()

    private var counterResetJob: Job? = null

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        Timber.d("Caught Exception :$e on Thread: $t")

        val exceptionCount = exceptionCounter.getAndIncrement()

        if (exceptionCount < MAX_FREQUENT_EXCEPTION_ALLOWED_COUNT) {
            Timber.d("exceptionCounter : $exceptionCount")

            counterResetJob?.cancel()

            counterResetJob = GlobalScope.launch {
                delay(EXCEPTION_COUNTER_RESET_DELAY_IN_MILLIS)

                exceptionCounter.set(0)

                Timber.d("Resetting exceptionCounter")
            }

            block.invoke()

            killAndExitProcessWithCleanStatus()
        } else {
            Timber.e("Throwing EXCEPTION_COUNTER_EXCEEDED_EXCEPTION")

            throw Throwable(EXCEPTION_COUNTER_EXCEEDED_EXCEPTION)
        }
    }

    companion object {
        private val TAG by lazy { this::class.java.canonicalName }

        private const val UNKNOWN_EXCEPTION = "UnknownException"
        private const val EXCEPTION_COUNTER_EXCEEDED_EXCEPTION = "ExceptionCounterExceededException"

        private const val MAX_FREQUENT_EXCEPTION_ALLOWED_COUNT = 3
        private const val EXCEPTION_COUNTER_RESET_DELAY_IN_MILLIS = 10000L
    }
}