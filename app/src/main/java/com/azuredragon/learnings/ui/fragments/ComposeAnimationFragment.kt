package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.azuredragon.learnings.R

class ComposeAnimationFragment: Fragment() {

    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = { navController.navigate(R.id.navigationTestActivity) }
                    ) {
                        Text(text = "Navigation Test")
                    }
                }
            }
        }
    }
}