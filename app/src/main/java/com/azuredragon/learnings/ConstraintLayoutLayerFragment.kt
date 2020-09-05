package com.azuredragon.learnings

import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import androidx.lifecycle.lifecycleScope
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.azuredragon.learnings.databinding.FragmentConstraintLayoutLayerBinding
import com.azuredragon.learnings.databinding.FragmentLottieBinding
import kotlinx.coroutines.delay

class ConstraintLayoutLayerFragment : Fragment() {

    private lateinit var constraintLayoutLayerBinding: FragmentConstraintLayoutLayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        constraintLayoutLayerBinding = FragmentConstraintLayoutLayerBinding.inflate(inflater, container, false)
        return constraintLayoutLayerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            delay(5000)

            TransitionManager.beginDelayedTransition(constraintLayoutLayerBinding.root as ViewGroup)
            constraintLayoutLayerBinding.layerButtons.scaleX = 0.5f
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintLayoutLayerFragment()
    }
}