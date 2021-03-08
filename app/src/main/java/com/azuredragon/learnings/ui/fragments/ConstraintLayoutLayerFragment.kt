package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentConstraintLayoutLayerBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.viewLifeCycleScope

class ConstraintLayoutLayerFragment: Fragment(R.layout.fragment_constraint_layout_layer) {

    private val constraintLayoutLayerBinding: FragmentConstraintLayoutLayerBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifeCycleScope?.launchWhenResumed {
            constraintLayoutLayerBinding.layerButtons.scaleX = 1.5f
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintLayoutLayerFragment()
    }
}