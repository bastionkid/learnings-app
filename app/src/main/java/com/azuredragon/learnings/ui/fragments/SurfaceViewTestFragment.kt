package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentSurfaceViewTestBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SurfaceViewTestFragment: Fragment(R.layout.fragment_surface_view_test) {

    private val surfaceViewTestBinding: FragmentSurfaceViewTestBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(5000)

            surfaceViewTestBinding.csv1.drawSomething(0)
            surfaceViewTestBinding.csv2.drawSomething(1)
            surfaceViewTestBinding.csv3.drawSomething(2)
            surfaceViewTestBinding.csv4.drawSomething(3)
            surfaceViewTestBinding.csv5.drawSomething(4)
            surfaceViewTestBinding.csv6.drawSomething(5)
        }
    }
}