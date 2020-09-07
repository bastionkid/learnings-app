package com.azuredragon.learnings.performance

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Debug
import android.os.Process
import androidx.annotation.RequiresApi
import com.azuredragon.learnings.LearningsApplication
import com.azuredragon.learnings.ktxextensions.bytesToMb
import timber.log.Timber

/**
 * Provides [ActivityManager.MemoryInfo] which has system level memory info like
 * total memory [ActivityManager.MemoryInfo.totalMem],
 * available memory [ActivityManager.MemoryInfo.availMem],
 * or whether system is running low on memory [ActivityManager.MemoryInfo.lowMemory]
 *
 * @return [ActivityManager.MemoryInfo]
 */
fun getSystemMemoryInfo(): ActivityManager.MemoryInfo {
    val activityManager = LearningsApplication.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    return ActivityManager.MemoryInfo().also { memoryInfo ->
        activityManager.getMemoryInfo(memoryInfo)
    }
}

/**
 * Provides [Debug.MemoryInfo] which has MemoryInfo for current app process
 *
 * @return [Debug.MemoryInfo]
 */
fun getMemoryInfoForCurrentProcess(): Debug.MemoryInfo {
    val activityManager = LearningsApplication.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    return activityManager.getProcessMemoryInfo(intArrayOf(Process.myPid()))[0]
}

/**
 * Provides [Debug.MemoryInfo.getMemoryStats] which has memory stats info for current app process
 *
 * @return Map<String, String> having
 */
@RequiresApi(Build.VERSION_CODES.M)
fun getMemoryStatsForCurrentProcess(): Map<String, String> {
    return getMemoryInfoForCurrentProcess().memoryStats
}

/**
 * Logs data returned by [getMemoryStatsForCurrentProcess] with provided [prefixTag] text
 *
 * @param [prefixTag] String to be used as prefix for the log
 */
@RequiresApi(Build.VERSION_CODES.M)
fun logMemoryStatsForCurrentProcess(prefixTag: String? = null) {
    Timber.d("${prefixTag ?: ""} Memory Stats : ${getMemoryStatsForCurrentProcess()}")
}

/**
 * Prints Useful info like [Runtime]'s
 * max heap size [Runtime.maxMemory],
 * total heap size [Runtime.totalMemory],
 * used heap size [Runtime.totalMemory] - [Runtime.freeMemory],
 * & free heap size [Runtime.freeMemory]
 * in MB
 *
 * @param [messagePrefix] String to be used as prefix for the log message
 */
fun logJvmHeapMemoryInfo(messagePrefix: String? = null) {
    Timber.d("${messagePrefix ?: ""} \nMax JVM heap size: %f MB, \nTotal JVM heap size: %f MB, \nUsed JVM heap size: %f MB, \nFree JVM heap size: %f MB",
            Runtime.getRuntime().maxMemory().bytesToMb(),
            Runtime.getRuntime().totalMemory().bytesToMb(),
            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()).bytesToMb(),
            Runtime.getRuntime().freeMemory().bytesToMb())
}

/**
 * Prints Useful stats like [Debug]'s
 * total native heap size [Debug.getNativeHeapSize],
 * allocated native heap size [Debug.getNativeHeapAllocatedSize],
 * used native heap size [Debug.getNativeHeapAllocatedSize] - [Debug.getNativeHeapFreeSize]
 * & free native heap size [Debug.getNativeHeapFreeSize]
 * in MB
 *
 * @param [messagePrefix] String to be used as prefix for the log message
 */
fun logNativeHeapMemoryInfo(messagePrefix: String? = null) {
    Timber.d("${messagePrefix ?: ""} \nMax Native heap size: %f MB, \nTotal Native heap size: %f MB, \nUsed Native heap size: %f MB, \nFree Native heap size: %f MB",
            Debug.getNativeHeapSize().bytesToMb(),
            Debug.getNativeHeapAllocatedSize().bytesToMb(),
            (Debug.getNativeHeapAllocatedSize() - Debug.getNativeHeapFreeSize()).bytesToMb(),
            Debug.getNativeHeapFreeSize().bytesToMb())
}

/**
 * Executes both [logJvmHeapMemoryInfo] & [logNativeHeapMemoryInfo]
 *
 * @param [messagePrefix] String to be used as prefix for the log message
 */
fun logBothJvmAndNativeHeapMemoryInfo(messagePrefix: String? = null) {
    logJvmHeapMemoryInfo(messagePrefix)
    logNativeHeapMemoryInfo(messagePrefix)
}