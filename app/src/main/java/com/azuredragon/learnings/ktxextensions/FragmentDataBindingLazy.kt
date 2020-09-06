package com.azuredragon.learnings.ktxextensions

import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class FragmentDataBindingLazy<T: ViewDataBinding>(private val fragment: Fragment): Lazy<T>, LifecycleObserver {

    private var cached: T? = null

    override val value: T
        get() {
            return if (cached != null) {
                cached!!
            } else {
                fragment.viewLifecycleOwner.lifecycle.addObserver(this)

                cached = DataBindingUtil.bind<T>(fragment.requireView())
                cached!!
            }

        }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        fragment.viewLifecycleOwner.lifecycle.removeObserver(this)

        Handler(Looper.getMainLooper()).post {
            cached = null
        }
    }

    override fun isInitialized(): Boolean = cached != null
}