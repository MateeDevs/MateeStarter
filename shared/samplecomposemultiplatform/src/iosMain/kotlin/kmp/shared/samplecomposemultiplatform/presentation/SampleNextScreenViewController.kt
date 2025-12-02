package kmp.shared.samplecomposemultiplatform.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.samplecomposemultiplatform.presentation.common.AppTheme
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleNextScreen
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextEvent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextIntent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleNextScreenViewController(
    onEvent: (SampleNextEvent) -> Unit,
): UIViewController {
    return ComposeUIViewController {
        SampleNextView(onEvent = onEvent)
    }
}

@Composable
internal fun SampleNextView(
    onEvent: (SampleNextEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: SampleNextViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onViewAppeared()
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            onEvent(event)
        }
    }

    AppTheme {
        SampleNextScreen(
            state = state,
            onIntent = viewModel::onIntent,
            modifier = modifier,
        )
    }
}
