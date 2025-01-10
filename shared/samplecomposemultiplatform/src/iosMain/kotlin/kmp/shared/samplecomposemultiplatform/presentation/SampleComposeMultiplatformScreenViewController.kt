package kmp.shared.samplecomposemultiplatform.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.samplecomposemultiplatform.presentation.common.AppTheme
import kmp.shared.samplecomposemultiplatform.presentation.ui.LocalSampleComposeMultiplatformViewFactory
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformScreen
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformViewFactory
import kmp.shared.samplesharedviewmodel.vm.SampleSharedEvent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedIntent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleComposeMultiplatformScreenViewController(
    onEvent: (SampleSharedEvent) -> Unit,
    factory: SampleComposeMultiplatformViewFactory,
): UIViewController {
    return ComposeUIViewController {
        SampleComposeMultiplatformView(
            onEvent = onEvent,
            factory = factory,
        )
    }
}

@Composable
internal fun SampleComposeMultiplatformView(
    onEvent: (SampleSharedEvent) -> Unit,
    factory: SampleComposeMultiplatformViewFactory,
    modifier: Modifier = Modifier,
) {
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
    CompositionLocalProvider(
        LocalSampleComposeMultiplatformViewFactory provides factory,
    ) {
        AppTheme {
            SampleComposeMultiplatformScreen(
                state = state,
                onIntent = viewModel::onIntent,
                modifier = modifier,
            )
        }
    }
}
