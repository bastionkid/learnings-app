package com.azuredragon.learnings.ktxextensions

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
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