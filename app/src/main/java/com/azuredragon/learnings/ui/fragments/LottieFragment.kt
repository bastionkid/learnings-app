package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentLottieBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.gone
import com.azuredragon.learnings.ktxextensions.navigateSafely
import kotlinx.coroutines.delay

class LottieFragment : Fragment(R.layout.fragment_lottie) {

    private val lottieBinding: FragmentLottieBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottieBinding.apply {
            setOnNextClickListener {
                navigateSafely(R.id.action_lottieFragment_to_constraintLayoutLayerFragment)
            }
        }

        lifecycleScope.launchWhenResumed {
            delay(10000)

            println("When Visibility = View.VISIBLE Total Memory: ${Runtime.getRuntime().totalMemory()}, Free Memory: ${Runtime.getRuntime().freeMemory()}")

            delay(10000)

            lottieBinding.lav.gone()

            delay(10000)

            println("After Visibility = View.GONE Total Memory: ${Runtime.getRuntime().totalMemory()}, Free Memory: ${Runtime.getRuntime().freeMemory()}")

            navigateSafely(R.id.action_lottieFragment_to_blankFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LottieFragment()
    }
}