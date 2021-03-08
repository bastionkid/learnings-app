package com.azuredragon.learnings.ktxextensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

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