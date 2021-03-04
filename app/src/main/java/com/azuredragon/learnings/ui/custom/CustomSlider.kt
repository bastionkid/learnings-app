package com.azuredragon.learnings.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.azuredragon.learnings.ktxextensions.dpToPx
import com.google.android.material.slider.Slider
import com.google.android.material.tooltip.TooltipDrawable

class CustomSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Slider(context, attrs, defStyleAttr) {

    init {
        try {
            val labelsRef = Slider::class.java.superclass?.getDeclaredField("labels")
            labelsRef?.isAccessible = true
            val labels = labelsRef?.get(this) as? List<TooltipDrawable>
            labels?.forEach { label ->
                label.setStroke(1.dpToPx(context).toFloat(), Color.parseColor("#CCCCCC"))
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }
}