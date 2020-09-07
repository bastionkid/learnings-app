package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentBlankBinding
import com.azuredragon.learnings.ktxextensions.dataBindings
import com.azuredragon.learnings.performance.logJvmHeapMemoryInfo
import com.azuredragon.learnings.performance.logNativeHeapMemoryInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BlankFragment : Fragment(R.layout.fragment_blank) {

    private val blankBinding: FragmentBlankBinding by dataBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(10000)

            logJvmHeapMemoryInfo("After 10s of onViewCreated() of ${BlankFragment::class.java.canonicalName}")
            logNativeHeapMemoryInfo("After 10s of onViewCreated() of ${BlankFragment::class.java.canonicalName}")

            System.gc()

            delay(10000)

            logJvmHeapMemoryInfo("After 10s of running manual GC of ${BlankFragment::class.java.canonicalName}")
            logNativeHeapMemoryInfo("After 10s of running manual GC of ${BlankFragment::class.java.canonicalName}")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }
}