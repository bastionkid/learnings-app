package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentFlowTestBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.getLifeCycleOwner
import com.azuredragon.learnings.ktxextensions.observeSafely
import com.azuredragon.learnings.ui.utils.LinearGradientSpan
import com.azuredragon.learnings.ui.viewmodels.FlowTestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FlowTestFragment: Fragment(R.layout.fragment_flow_test) {

    private val flowTestBinding: FragmentFlowTestBinding by dataBindingsLazy()

    private val flowTestViewModel: FlowTestViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val containingText = getString(R.string.label_some_nice_gradient_span_text)
        val textToStyle = getString(R.string.label_gradient_span)

        val spannable = containingText.toSpannable()

        val startIndexOfTextToStyle = containingText.indexOf(textToStyle)

        if (startIndexOfTextToStyle >= 0) {
            val endIndexOfTextToStyle = startIndexOfTextToStyle + textToStyle.length
            spannable[startIndexOfTextToStyle..endIndexOfTextToStyle] = LinearGradientSpan(containingText, startIndexOfTextToStyle, endIndexOfTextToStyle, ContextCompat.getColor(requireContext(), R.color.purple_200), ContextCompat.getColor(requireContext(), R.color.teal_200))

            flowTestBinding.tvGradientSpan.text = spannable
        }

        flowTestViewModel.randomLiveData.observeSafely(getLifeCycleOwner()) { /* no-op */ }

        flowTestViewModel.retryLiveData.observeSafely(getLifeCycleOwner()) { /* no-op */ }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FlowTestFragment()
    }
}