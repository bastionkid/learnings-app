package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentFlowTestBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.getLifeCycleOwner
import com.azuredragon.learnings.ktxextensions.observeSafely
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FlowTestFragment: Fragment(R.layout.fragment_flow_test) {

    private val flowTestBinding: FragmentFlowTestBinding by dataBindingsLazy()

    private val flowTestViewModel: FlowTestViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flowTestViewModel.randomLiveData.observeSafely(getLifeCycleOwner()) {
            flowTestBinding.data = it
        }

        flowTestViewModel.retryLiveData.observeSafely(getLifeCycleOwner()) { /* no-op */ }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FlowTestFragment()
    }
}