package com.azuredragon.learnings.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.ValueAnimator
import androidx.core.content.ContextCompat
import com.azuredragon.learnings.R

class RingLoader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0): View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint: Paint

    private var circleRadius: Float

    private val animationInterpolator = AccelerateDecelerateInterpolator()

    private var valueAnimator: ValueAnimator? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingLoader)
        val strokeColor = typedArray.getColor(R.styleable.RingLoader_rl_stroke_color, ContextCompat.getColor(context, R.color.purple_700))
        val strokeWidth = typedArray.getFloat(R.styleable.RingLoader_rl_stroke_width, 20F)

        circleRadius = typedArray.getFloat(R.styleable.RingLoader_rl_circle_radius, 100F)

        paint = Paint().apply {
            this.strokeWidth = strokeWidth
            color = strokeColor
            style = Paint.Style.STROKE
        }

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(width / 2F,height / 2F, circleRadius, paint)
    }

    fun showLoading() {
        valueAnimator = ValueAnimator.ofFloat(20F, circleRadius).apply {
            duration = 1000
            interpolator = animationInterpolator
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                circleRadius = animatedValue as Float

                invalidate()
            }
        }
        valueAnimator?.start()
    }
}