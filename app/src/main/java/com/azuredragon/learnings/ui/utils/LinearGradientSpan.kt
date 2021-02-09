package com.azuredragon.learnings.ui.utils

import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import androidx.annotation.ColorInt
import timber.log.Timber

class LinearGradientSpan(
    private val containingText: String,
    private val startIndex: Int,
    private val endIndex: Int,
    @ColorInt private val startColorInt: Int,
    @ColorInt private val endColorInt: Int
): CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(textPaint: TextPaint?) {
        textPaint?.let {
            if (startIndex >= 0) {
                val leadingWidth = if (startIndex > 0) textPaint.measureText(containingText, 0, startIndex) else 0f

                val gradientWidth = textPaint.measureText(containingText, startIndex, endIndex)

                textPaint.shader = LinearGradient(
                    leadingWidth,
                    0f,
                    leadingWidth + gradientWidth,
                    0f,
                    startColorInt,
                    endColorInt,
                    Shader.TileMode.REPEAT
                )
            } else {
                Timber.w("Invalid index = $startIndex passed to provide LinearGradientSpan in $containingText")
            }
        }
    }
}