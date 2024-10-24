package kmp.shared.samplecomposenavigation.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.ui.SampleNextScreen
import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextEvent
import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextIntent
import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SampleNextView(
    onEvent: (SampleNextEvent) -> Unit,
) {
    val viewModel: SampleNextViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(SampleNextIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            onEvent(event)
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = "Next",
                    onBackClick = { viewModel.onIntent(SampleNextIntent.OnBackTapped) },
                )
            },
        ) {
            SampleNextScreen(state = state, onIntent = viewModel::onIntent)
        }
    }
}
