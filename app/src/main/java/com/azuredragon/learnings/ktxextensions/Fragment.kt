package com.azuredragon.learnings.ktxextensions

import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.azuredragon.learnings.delegates.FragmentDataBinding
import com.azuredragon.learnings.delegates.FragmentDataBindingLazy
import kotlin.properties.ReadOnlyProperty

fun Fragment.navigateSafely(destinationId: Int) {
    if (isAdded) findNavController().navigate(destinationId)
}

fun Fragment.getLifeCycleOwner(): LifecycleOwner? {
    return try {
        viewLifecycleOwner
    } catch (e: IllegalStateException) {
        null
    }
}

val Fragment.viewLifeCycleScope: LifecycleCoroutineScope?
    get() = getLifeCycleOwner()?.lifecycleScope

val Fragment.viewLifeCycle: Lifecycle?
    get() = getLifeCycleOwner()?.lifecycle

@MainThread
fun <T: ViewDataBinding> Fragment.dataBindingsLazy(): Lazy<T> {
    return FragmentDataBindingLazy(this)
}

@MainThread
fun <T: ViewDataBinding> Fragment.dataBindings(): ReadOnlyProperty<Fragment, T> {
    return FragmentDataBinding()
}