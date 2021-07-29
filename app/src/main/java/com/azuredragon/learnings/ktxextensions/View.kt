package com.azuredragon.learnings.ktxextensions

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
import com.azuredragon.learnings.R
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

@ExperimentalCoroutinesApi
suspend fun View.awaitTillNextLayout() = suspendCancellableCoroutine<Unit> { it ->
    val listener = object: View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            removeOnLayoutChangeListener(this)

            it.resume(Unit) {
                removeOnLayoutChangeListener(this)
            }
        }
    }

    it.invokeOnCancellation {
        removeOnLayoutChangeListener(listener)
    }

    addOnLayoutChangeListener(listener)
}

@ExperimentalCoroutinesApi
suspend fun RecyclerView.Adapter<RecyclerView.ViewHolder>.getItemPosition(itemId: Long): Int {
    //TODO Author:akashkhunt Date:22/12/20 What Needs to be Done:Check if the existing data already has the itemId
    val position = 0

    if (position >= 0) return position

    return suspendCancellableCoroutine {
        val adapterDataObserver = object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                for (itemPosition in positionStart..(positionStart + itemCount)) {
                    if (itemId == getItemId(itemPosition)) {
                        unregisterAdapterDataObserver(this)

                        it.resume(itemPosition) {
                            unregisterAdapterDataObserver(this)
                        }

                        return
                    }
                }
            }
        }

        it.invokeOnCancellation {
            unregisterAdapterDataObserver(adapterDataObserver)
        }

        registerAdapterDataObserver(adapterDataObserver)
    }
}

@ExperimentalCoroutinesApi
suspend fun View.awaitNextAnimationFrame() = suspendCancellableCoroutine<Unit> {
    val runnable = object: Runnable {
        override fun run() {
            removeCallbacks(this)

            it.resume(Unit) {
                removeCallbacks(this)
            }
        }
    }

    it.invokeOnCancellation {
        removeCallbacks(runnable)
    }

    postOnAnimation(runnable)
}

@ExperimentalCoroutinesApi
suspend fun RecyclerView.awaitTillScrollEnd() {
    // If a smooth scroll has just been started, it won't actually start until the next
    // animation frame, so we'll await that first
    awaitNextAnimationFrame()

    // Now we can check if we're actually idle. If so, return now
    if (scrollState == RecyclerView.SCROLL_STATE_IDLE) return

    suspendCancellableCoroutine<Unit> {
        val scrollListener = object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    removeOnScrollListener(this)

                    it.resume(Unit) {
                        removeOnScrollListener(this)
                    }
                }
            }
        }

        it.invokeOnCancellation {
            removeOnScrollListener(scrollListener)
        }

        addOnScrollListener(scrollListener)
    }
}

fun View.setAndStartAvdBgAnim(drawableRes: Int) {
    val animatedVectorDrawable = SeekableAnimatedVectorDrawable.create(context, drawableRes)

    animatedVectorDrawable?.let {
        background = animatedVectorDrawable

        animatedVectorDrawable.start()
    }
}

fun View.setAndStartAvdBgAnimWithRestart(
    drawableRes: Int,
    lifecycleScope: LifecycleCoroutineScope
) {
    val animatedVectorDrawable = SeekableAnimatedVectorDrawable.create(context, drawableRes)

    animatedVectorDrawable?.let {
        background = animatedVectorDrawable

        animatedVectorDrawable.registerAnimationCallback(object :
            SeekableAnimatedVectorDrawable.AnimationCallback() {
            override fun onAnimationEnd(drawable: SeekableAnimatedVectorDrawable) {
                super.onAnimationEnd(drawable)

                lifecycleScope.launchWhenResumed {
                    Timber.d("setAndStartAvdBackgroundAnimationWithRestart start()")

                    animatedVectorDrawable.start()
                }
            }
        })

        animatedVectorDrawable.start()
    }
}

fun View.stopAvdBgAnim() {
    (background as? SeekableAnimatedVectorDrawable)?.stop()
}

fun View.stopAvdBgAnimWithDrawable(@DrawableRes endAnimDrawable: Int) {
    stopAvdBgAnim()

    background = ContextCompat.getDrawable(context, endAnimDrawable)
}

/**
 * Helps in applying Blurred background to the [ImageView] on which this function is invoked
 *
 * @param backgroundView the [View] which will be used to create a Blurred Bitmap
 * @param radius used as an argument to [Blurry]
 * @param sampling used as an argument to [Blurry]
 */
fun ImageView.applyBlurredBackground(backgroundView: View, radius: Int = 10, sampling: Int = 2, fadeInDurationInMillis: Long = 0) {
    scaleType = ImageView.ScaleType.CENTER_CROP

    if (backgroundView.background == null) backgroundView.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700))

    backgroundView.doOnPreDraw {
        Blurry.with(context)
            .radius(radius)
            .sampling(sampling)
            .color(ContextCompat.getColor(context, R.color.teal_700))
            .capture(backgroundView)
            .into(this)
    }

    doOnNextLayout {
        if (fadeInDurationInMillis > 0L) {
            animate().alpha(1f).setDuration(fadeInDurationInMillis).start()
        }
    }
}

/**
 * Helps in applying Blurred background to the [ViewGroup] on which this function is invoked
 *
 * Currently applyBlurredBackground() is only supported to [FrameLayout], [RelativeLayout] & [ConstraintLayout].
 *
 * @param backgroundView the [View] which will be used to create a Blurred Bitmap
 * @param radius used as an argument to [Blurry]
 * @param sampling used as an argument to [Blurry]
 *
 * @throws RuntimeException when this function is invoked on an invalid [ViewGroup]
 */
fun ViewGroup.applyBlurredBackground(backgroundView: View, radius: Int = 10, sampling: Int = 2, fadeInDurationInMillis: Long = 0) {
    val imageView = ImageView(context).apply {
        id = View.generateViewId()
        alpha = if (fadeInDurationInMillis == 0L) 1f else 0f
    }

    when (this) {
        is FrameLayout, is RelativeLayout, is ConstraintLayout -> addView(imageView, 0)
        else -> throw RuntimeException("Currently applyBlurredBackground() is only supported for FrameLayout, RelativeLayout & ConstraintLayout.")
    }

    imageView.applyBlurredBackground(backgroundView, radius, sampling, fadeInDurationInMillis)
}