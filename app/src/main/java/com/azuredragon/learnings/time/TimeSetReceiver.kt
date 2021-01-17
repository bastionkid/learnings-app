package com.azuredragon.learnings.time

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class TimeSetReceiver(private val context: Context) : BroadcastReceiver(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun register() {
        Timber.d( "registering receiver")
        context.registerReceiver(this, IntentFilter().apply {
            addAction("android.intent.action.TIME_SET")
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unregister() {
        Timber.d( "unregistering receiver")
        try {
            context.unregisterReceiver(this)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d( "onReceive intent=$intent")
        Clock.synchronize(context)
    }

    companion object {
        private val TAG by lazy { TimeSetReceiver::class.java.canonicalName }
    }

}