package com.azuredragon.learnings.utils

import timber.log.Timber

class DataBindingAccessExceptionHandler(private val block: (Thread?, Throwable?) -> Unit): Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread?, throwable: Throwable?) {
        Timber.d("Caught Exception: $throwable on Thread: $thread")

        var rootThrowable = throwable

        while (rootThrowable?.cause != null) {
            rootThrowable = rootThrowable.cause
            Timber.d("Caught root cause Exception: $rootThrowable on Thread: $thread")
        }

        if (rootThrowable !is IllegalDataBindingAccessException) {
            block.invoke(thread, throwable)
        }
    }

    companion object {
        private val TAG by lazy { this::class.java.canonicalName }

        private const val UNKNOWN_EXCEPTION = "UnknownException"
    }
}

class IllegalDataBindingAccessException(message: String): Exception(message)