package com.azuredragon.learnings

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class LearningsApplication: Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + " : " + element.lineNumber
                }
            })
        }
    }

    companion object {
        var instance: LearningsApplication? = null
    }
}