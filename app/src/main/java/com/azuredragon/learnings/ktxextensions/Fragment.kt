package com.azuredragon.learnings.ktxextensions

import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.properties.ReadOnlyProperty

fun Fragment.navigateSafely(destinationId: Int) {
    if (isAdded) findNavController().navigate(destinationId)
}

@MainThread
fun <T: ViewDataBinding> Fragment.dataBindingsLazy(): Lazy<T> {
    return FragmentDataBindingLazy(this)
}

@MainThread
fun <T: ViewDataBinding> Fragment.dataBindings(): ReadOnlyProperty<Fragment, T> {
    return FragmentDataBinding()
}