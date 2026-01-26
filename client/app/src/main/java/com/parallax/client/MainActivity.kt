package com.parallax.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.parallax.client.presentation.theme.ParallaxTheme
import com.parallax.client.presentation.ui.StreamScreen
import com.parallax.client.presentation.vm.StreamViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParallaxTheme {
                val viewModel: StreamViewModel = viewModel()
                val uiState = viewModel.uiState
                    .collectAsStateWithLifecycle()
                    .value

                Surface(modifier = Modifier.fillMaxSize()) {
                    StreamScreen(
                        uiState = uiState,
                        onStart = viewModel::onStartClicked,
                        onStop = viewModel::onStopClicked,
                        onScaleChanged = viewModel::onScaleChanged,
                    )
                }
            }
        }
    }
}
