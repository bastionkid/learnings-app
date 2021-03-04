package com.azuredragon.learnings.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import com.azuredragon.learnings.ktxextensions.dpToPx
import com.google.android.material.internal.DescendantOffsetUtils
import com.google.android.material.internal.ViewUtils
import com.google.android.material.slider.Slider
import com.google.android.material.tooltip.TooltipDrawable

@SuppressLint("RestrictedApi")
class CustomSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Slider(context, attrs, defStyleAttr) {

    init {
        try {
            val labelsRef = Slider::class.java.superclass?.getDeclaredField("labels")
            labelsRef?.isAccessible = true
            val labels = labelsRef?.get(this) as? List<TooltipDrawable>
            labels?.forEach { label ->
                label.setStroke(1.dpToPx(context).toFloat(), Color.parseColor("#CCCCCC"))
            }

            addOnChangeListener { _, value, _ ->
                try {
                    labels?.firstOrNull()?.let { label ->
                        label.text = "\u20B9 $value"

                        val trackTopRef = Slider::class.java.superclass?.getDeclaredField("trackTop")
                        trackTopRef?.isAccessible = true
                        val trackTop = trackTopRef?.get(this) as? Int

                        val labelPaddingRef = Slider::class.java.superclass?.getDeclaredField("labelPadding")
                        labelPaddingRef?.isAccessible = true
                        val labelPadding = labelPaddingRef?.get(this) as? Int

                        val normalizeValueRef = Slider::class.java.superclass?.getDeclaredMethod("normalizeValue", Float::class.java)
                        normalizeValueRef?.isAccessible = true

                        val left = (trackSidePadding + (((normalizeValueRef?.invoke(this, value) as? Float) ?: 0f) * trackWidth).toInt() - label.intrinsicWidth / 2)
                        val top = (trackTop ?: 0) - ((labelPadding ?: 0) + (thumbRadius * 2.5).toInt())
                        label.setBounds(left, top - label.intrinsicHeight, left + label.intrinsicWidth, top)

                        // Calculate the difference between the bounds of this view and the bounds of the root view to
                        // correctly position this view in the overlay layer.

                        // Calculate the difference between the bounds of this view and the bounds of the root view to
                        // correctly position this view in the overlay layer.
                        val rect = Rect(label.bounds)
                        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this)!!, this, rect)

                        label.bounds = rect

                        ViewUtils.getContentViewOverlay(this)?.add(label)
                    }
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }
}