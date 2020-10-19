package com.azuredragon.learnings.ktxextensions

import android.os.Process
import kotlin.system.exitProcess

fun killAndExitProcessWithCleanStatus() {
    killAndExitProcessWithStatus(0)
}

fun killAndExitProcessWithStatus(status: Int) {
    Process.killProcess(Process.myPid())
    exitProcess(status)
}