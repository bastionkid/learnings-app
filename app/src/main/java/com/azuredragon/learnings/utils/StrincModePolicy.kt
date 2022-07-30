package com.azuredragon.learnings.utils

import android.os.StrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode
import com.azuredragon.learnings.BuildConfig

fun enableStrictModePolicy() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectCustomSlowCalls()
            .detectNetwork() // or .detectAll() for all detectable problems
            .apply {
                if (BuildConfig.DEBUG) {
                    penaltyLog()
                } else {
                    //TODO(akashkhunt): 22/05/22 Log an event for this
                }
            }
            .build()
    )

    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectActivityLeaks()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects() // or .detectAll() for all detectable problems
            .apply {
                if (BuildConfig.DEBUG) {
                    penaltyLog()
                } else {
                    //TODO(akashkhunt): 22/05/22 Log an event for this
                }
            }
            .build()
    )

    FragmentStrictMode.defaultPolicy = FragmentStrictMode.Policy.Builder()
        .detectRetainInstanceUsage()
        .detectSetUserVisibleHint()
        .detectTargetFragmentUsage()
        .apply {
            if (BuildConfig.DEBUG) {
                penaltyLog()
            } else {
                //TODO(akashkhunt): 22/05/22 Log an event for this
            }
        }
        .build()
}