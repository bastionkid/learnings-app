package com.azuredragon.learnings.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import timber.log.Timber

class ResizeGlobalLayoutListener(
    private val decorView: View,
    private val contentView: View,
    private val onDecorHeightReduced: (Boolean) -> Unit,
) : ViewTreeObserver.OnGlobalLayoutListener {

    var initialHeight = Int.MIN_VALUE

    override fun onGlobalLayout() {
        val rect = Rect()
        //rect will be populated with the coordinates of your view that area still visible.
        decorView.getWindowVisibleDisplayFrame(rect)

        if (initialHeight == Int.MIN_VALUE) initialHeight = rect.bottom

        //get screen height and calculate the difference with the usable area from the rect
        val diff: Int = initialHeight - rect.bottom

        Timber.d("diff: $diff")

        //if it could be a keyboard add the padding to the view
        if (diff != 0) {
            // if the use-able screen height differs from the total screen height we assume that it shows a keyboard now
            //check if the padding is 0 (if yes set the padding for the keyboard)
            if (contentView.paddingBottom != diff) {
                //set the padding of the contentView for the keyboard
//                contentView.setPadding(0, 0, 0, diff)
            }
            onDecorHeightReduced.invoke(true)
        } else {
            //check if the padding is != 0 (if yes reset the padding)
            if (contentView.paddingBottom != 0) {
                //reset the padding of the contentView
//                contentView.setPadding(0, 0, 0, 0)
            }
            onDecorHeightReduced.invoke(false)
        }
    }
}