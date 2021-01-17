package com.azuredragon.learnings

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.azuredragon.learnings.performance.logNativeHeapMemoryInfo
import com.azuredragon.learnings.time.Clock
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

        Clock.everySecond.observe(ProcessLifecycleOwner.get()) {
            logNativeHeapMemoryInfo("Bitmap Recycling")
        }
    }

    companion object {
        var instance: LearningsApplication? = null

        fun getApplicationContext(): Context = instance!!.applicationContext
    }
}