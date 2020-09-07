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
import com.azuredragon.learnings.performance.logJvmHeapMemoryInfo
import com.azuredragon.learnings.performance.logNativeHeapMemoryInfo
import kotlinx.coroutines.delay

class LottieFragment : Fragment(R.layout.fragment_lottie) {

    private val lottieBinding: FragmentLottieBinding by dataBindingsLazy()
//
//    private lateinit var lottieBinding: FragmentLottieBinding
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        lottieBinding = FragmentLottieBinding.inflate(inflater, container, false)
//        return lottieBinding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lottieBinding.apply {
            setOnNextClickListener {
                navigateSafely(R.id.action_lottieFragment_to_constraintLayoutLayerFragment)
            }
        }

        lifecycleScope.launchWhenResumed {
            delay(10000)

            logJvmHeapMemoryInfo("When Visibility = View.VISIBLE")
            logNativeHeapMemoryInfo("When Visibility = View.VISIBLE")

            delay(10000)

            lottieBinding.lav.gone()

            delay(10000)

            logJvmHeapMemoryInfo("After Visibility = View.GONE")
            logNativeHeapMemoryInfo("After Visibility = View.GONE")

            navigateSafely(R.id.action_lottieFragment_to_blankFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LottieFragment()
    }
}