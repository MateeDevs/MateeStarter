package kmp.shared.samplecomposemultiplatform.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.samplecomposemultiplatform.presentation.common.AppTheme
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformScreen
import kmp.shared.samplesharedviewmodel.vm.SampleSharedEvent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedIntent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleComposeMultiplatformScreenViewController(
    onEvent: (SampleSharedEvent) -> Unit,
): UIViewController {
    return ComposeUIViewController {
        val viewModel: SampleSharedViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(viewModel) {
            viewModel.onIntent(SampleSharedIntent.OnAppeared)
        }

        LaunchedEffect(viewModel) {
            viewModel.events.collectLatest { event ->
                onEvent(event)
            }
        }

        AppTheme {
            SampleComposeMultiplatformScreen(state = state, onIntent = viewModel::onIntent)
        }
    }
}
