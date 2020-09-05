package com.azuredragon.learnings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.databinding.FragmentLottieBinding
import com.azuredragon.learnings.ktxextensions.gone
import kotlinx.coroutines.delay

class LottieFragment : Fragment() {

    private lateinit var lottieBinding: FragmentLottieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lottieBinding = FragmentLottieBinding.inflate(inflater, container, false)
        return lottieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(30000)

            lottieBinding.lav.gone()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LottieFragment()
    }
}