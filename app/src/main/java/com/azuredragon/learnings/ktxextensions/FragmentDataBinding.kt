package com.azuredragon.learnings.ktxextensions

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentDataBinding<T: ViewDataBinding>: ReadOnlyProperty<Fragment, T>, LifecycleObserver {

    private var cached: T? = null

    private var lifecycleOwner: LifecycleOwner? = null

    @MainThread
    override fun getValue(fragment: Fragment, property: KProperty<*>): T {
        val viewDataBinding = cached

        return if (viewDataBinding != null) {
            viewDataBinding
        } else {
            lifecycleOwner = fragment.viewLifecycleOwner

            lifecycleOwner?.lifecycle?.addObserver(this)

            DataBindingUtil.bind<T>(fragment.requireView()).apply {
                cached = this
            }!!
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        lifecycleOwner?.lifecycle?.removeObserver(this)

        Handler(Looper.getMainLooper()).post {
            cached = null
        }
    }
}