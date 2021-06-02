package com.azuredragon.learnings.ui.custom

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceView
import com.azuredragon.learnings.R
import com.azuredragon.learnings.ktxextensions.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomSurfaceView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr) {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val resources = intArrayOf(
        R.drawable.ic_aadhar,
        R.drawable.ic_aadhar_back,
        R.drawable.ic_passport,
        R.drawable.ic_pan_card,
        R.drawable.ic_driving_license,
        R.drawable.ic_voter_id,
        R.drawable.ic_voter_id_back
    )

    fun drawSomething(index: Int = 0) {
        coroutineScope.launch {
            if (holder.surface.isValid) {
                val bitmap = BitmapFactory.decodeResource(context.resources, resources[index])

                val canvas = holder.lockCanvas()

                canvas.save()
                canvas.drawBitmap(bitmap, null, Rect(0, 0, 120.dpToPx(context), 120.dpToPx(context)), null)

                holder.unlockCanvasAndPost(canvas)

                val newIndex = (index + 1) % resources.size

                drawSomething(newIndex)

                bitmap.recycle()
            }
        }
    }
}