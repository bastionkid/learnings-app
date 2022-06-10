package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.azuredragon.learnings.ui.viewmodels.SnapshotTestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class SnapshotTestFragment: Fragment() {

    private val snapshotTestViewModel: SnapshotTestViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycleScope.launch(Dispatchers.Main) {
            snapshotTestViewModel
        }

        return ComposeView(requireContext()).apply {
            setContent {
                val uiState = snapshotTestViewModel.uiState.value

                SideEffect {
                    Timber.d("setContent composable finished")
                }

                Surface {
                    Column {
                        Text(text = "Hello World")
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SnapshotTestFragment()
    }
}