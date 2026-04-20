package kmp.shared.samplefeature.presentation

import androidx.compose.ui.window.ComposeUIViewController
import kmp.shared.samplefeature.presentation.ui.SampleFeatureRoute
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleFeatureMainScreenViewController(
    viewModel: SampleFeatureViewModel,
    onShowMessage: (String) -> Unit,
): UIViewController =
    ComposeUIViewController {
        SampleFeatureRoute(
            viewModel = viewModel,
            onShowMessage = onShowMessage,
        )
    }
