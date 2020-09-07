package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.bytesToMb
import com.azuredragon.learnings.ktxextensions.dataBindings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class BlankFragment : Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(10000)

            Timber.d("After 10s of opening BlankFragment Total Memory: ${Runtime.getRuntime().totalMemory().bytesToMb()}, Used Memory: ${(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()).bytesToMb()}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}