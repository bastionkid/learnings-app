package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy

class BlankFragment: Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blankBinding.slider.setLabelFormatter { "\u20B9 $it" }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}