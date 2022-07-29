package com.azuredragon.learnings.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class ComposeInsetsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons,
                        isNavigationBarContrastEnforced = false,
                    )
                }

                RootContent()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RootContent() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow.copy(alpha = 0.2f))
    ) {
        Column(Modifier.systemBarsPadding().navigationBarsPadding().padding(40.dp).consumedWindowInsets(PaddingValues(40.dp))) {
            var text by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = text,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(16.dp),
                onValueChange = { text = it },
            )

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .background(Color.Yellow.copy(alpha = 0.3f)),
                ) {
                    repeat(20) {
                        item {
                            Box(modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp)
                                .background(Color.Magenta)) {

                            }
                        }
                    }
                }
            }
        }
    }
}