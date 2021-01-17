package com.azuredragon.learnings.ktxextensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Long.bytesToMb(): Double = this.toDouble() / (1024 * 1024)

fun Long.kbToMb(): Double = this.toDouble() / 1024

fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

fun Int.pxToDp(displayMetrics: DisplayMetrics): Int = (this / displayMetrics.density).toInt()

fun Int.spToPx(context: Context): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics)

