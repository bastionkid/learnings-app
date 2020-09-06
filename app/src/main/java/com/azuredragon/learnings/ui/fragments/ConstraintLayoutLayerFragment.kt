package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentConstraintLayoutLayerBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import kotlinx.coroutines.delay

class ConstraintLayoutLayerFragment : Fragment(R.layout.fragment_constraint_layout_layer) {

    private val constraintLayoutLayerBinding: FragmentConstraintLayoutLayerBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(10000)

            TransitionManager.beginDelayedTransition(constraintLayoutLayerBinding.root as ViewGroup)
            constraintLayoutLayerBinding.layerButtons.scaleX = 0.5f
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintLayoutLayerFragment()
    }
}