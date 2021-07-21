package com.azuredragon.learnings.utils

import android.os.Parcel
import android.text.ParcelableSpan
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import androidx.annotation.FloatRange

class RelativeSizeWithCenterAlignmentSpan(@FloatRange(from = 0.4, to = 1.0) private val mProportion: Float) : MetricAffectingSpan(), ParcelableSpan {
    override fun updateDrawState(textPaint: TextPaint?) {
        textPaint?.let {
            updateTextPaint(textPaint)
        }
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        updateTextPaint(textPaint)
    }

    private fun updateTextPaint(textPaint: TextPaint) {
        textPaint.apply {
            val actualAscent = ascent()
            textSize *= mProportion
            baselineShift += ((actualAscent + textSize) / 2).toInt()
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}

    override fun getSpanTypeId(): Int = 99999999
}