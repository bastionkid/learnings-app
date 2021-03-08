package com.azuredragon.learnings.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.azuredragon.learnings.R
import com.azuredragon.learnings.databinding.FragmentExceptionThrowAndCatchBinding
import com.azuredragon.learnings.ktxextensions.dataBindingsLazy
import com.azuredragon.learnings.ktxextensions.viewLifeCycleScope
import com.azuredragon.learnings.ui.utils.UncaughtExceptionHandler
import kotlinx.coroutines.delay

class ExceptionThrowAndCatchFragment: Fragment(R.layout.fragment_exception_throw_and_catch) {

    private val exceptionThrowAndCatchBinding: FragmentExceptionThrowAndCatchBinding by dataBindingsLazy()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exceptionThrowAndCatchBinding.exceptionText = getString(R.string.label_throwing_exception)

        viewLifeCycleScope?.launchWhenResumed {
            delay(5000)

            throw Exception()
        }

        Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler {
            requireActivity().finish()

            val intent = requireActivity().packageManager.getLaunchIntentForPackage(requireActivity().packageName)

            startActivity(intent?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExceptionThrowAndCatchFragment()
    }
}