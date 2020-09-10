package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentLottieBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.navigateSafely

class LottieFragment : Fragment(R.layout.fragment_lottie) {

    private val lottieBinding: FragmentLottieBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottieBinding.apply {
            setOnNextClickListener {
                navigateSafely(R.id.action_lottieFragment_to_constraintLayoutLayerFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LottieFragment()
    }
}