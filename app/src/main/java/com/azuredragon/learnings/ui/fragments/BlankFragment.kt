package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.awaitTillNextLayout
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.viewLifeCycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

class BlankFragment: Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindingsLazy()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blankBinding.slider.setLabelFormatter { "₹ $it" }

        viewLifeCycleScope?.launchWhenResumed {
            view.awaitTillNextLayout()

            blankBinding.slider.value = 50f
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}