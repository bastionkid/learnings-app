package com.azuredragon.learnings.delegates

import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.azuredragon.learnings.ktxextensions.viewLifeCycle
import com.azuredragon.learnings.utils.IllegalDataBindingAccessException
import timber.log.Timber

class FragmentDataBindingLazy<T: ViewDataBinding>(private val fragment: Fragment): Lazy<T>, LifecycleObserver {

    private var cached: T? = null

    private var isViewDestroying = false

    private val TAG by lazy { fragment::class.java.simpleName }

    override val value: T
        get() {
            return if (cached != null && !isViewDestroying) {
                cached!!
            } else {
                Timber.d("creating new ViewDataBinding")

                isViewDestroying = false

                fragment.viewLifeCycle?.let { viewLifeCycle ->
                    viewLifeCycle.addObserver(this)

                    cached = DataBindingUtil.bind(fragment.requireView())
                    cached!!
                } ?: throw IllegalDataBindingAccessException("IllegalDataBindingAccessException for $TAG")
            }
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        Timber.d("onViewDestroyed() event received")
        fragment.viewLifeCycle?.removeObserver(this)

        isViewDestroying = true

        Handler(Looper.getMainLooper()).post {
            if (isViewDestroying) {
                cached = null
                Timber.d("cached ViewDataBinding made null using Looper")
            }
        }
    }

    override fun isInitialized(): Boolean = cached != null
}