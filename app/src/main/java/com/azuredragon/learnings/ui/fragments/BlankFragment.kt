package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.dataBindings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BlankFragment : Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(10000)

            println("After 10s of opening BlankFragment Total Memory: ${Runtime.getRuntime().totalMemory()}, Free Memory: ${Runtime.getRuntime().freeMemory()}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}