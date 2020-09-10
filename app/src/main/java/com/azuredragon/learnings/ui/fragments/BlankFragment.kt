package com.azuredragon.learnings.ui.fragments

import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy

class BlankFragment : Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindingsLazy()

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}