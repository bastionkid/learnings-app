package com.azuredragon.learnings.ktxextensions

fun Long.bytesToMb(): Double = this.toDouble() / (1024 * 1024)