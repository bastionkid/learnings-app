package com.azuredragon.learnings.delegates

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
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val viewDataBinding = cached

        return if (viewDataBinding != null) {
            viewDataBinding
        } else {
            lifecycleOwner = thisRef.viewLifecycleOwner

            lifecycleOwner?.lifecycle?.addObserver(this)

            DataBindingUtil.bind<T>(thisRef.requireView()).apply {
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