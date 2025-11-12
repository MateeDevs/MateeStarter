package kmp.shared.samplefeature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.base.presentation.ui.AppTheme
import kmp.shared.samplefeature.presentation.ui.SampleFeatureMainScreen
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleFeatureMainScreenViewController(
    viewModel: SampleFeatureViewModel,
): UIViewController {
    return ComposeUIViewController {
        val state by viewModel.state.collectAsStateWithLifecycle()

        AppTheme {
            SampleFeatureMainScreen(
                state = state,
                onIntent = viewModel::onIntent,
            )
        }
    }
}
