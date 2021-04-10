package com.azuredragon.learnings.utils

import android.content.Context
import android.net.ConnectivityManager
import com.azuredragon.learnings.LearningsApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.InetAddress
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
fun pingHost(scope: CoroutineScope, host: String, timeoutInMillis: Int, delayInMillis: Long, check: () -> Boolean) {
    val connectivityManager = LearningsApplication.instance?.applicationContext?.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager.activeNetworkInfo?.isConnected == true) {
        scope.launch(Dispatchers.IO) {
            while (check.invoke()) {
                delay(delayInMillis)

                val pingDuration = measureTime {
                    InetAddress.getByName(host).isReachable(timeoutInMillis)
                }

                if (InetAddress.getByName(host).isReachable(timeoutInMillis)) {
                    Timber.d("Ping successful, pingDuration: $pingDuration")
                } else {
                    Timber.d("Ping failed, pingDuration: $pingDuration")
                }
            }
        }
    }
}