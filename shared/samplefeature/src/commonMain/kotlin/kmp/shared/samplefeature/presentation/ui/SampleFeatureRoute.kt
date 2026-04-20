package kmp.shared.samplefeature.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.base.presentation.ui.AppTheme
import kmp.shared.samplefeature.presentation.vm.SampleFeatureEvent
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import kotlinx.coroutines.flow.onSubscription
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SampleFeatureRoute(
    viewModel: SampleFeatureViewModel = koinViewModel(),
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val toolbar by viewModel.toolbar.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.events
            .onSubscription {
                viewModel.onViewAppeared()
            }
            .collect { event ->
                when (event) {
                    is SampleFeatureEvent.ShowMessage -> onShowMessage(event.message)
                }
            }
    }

    AppTheme {
        SampleFeatureMainScreen(
            state = state,
            toolbar = toolbar,
            onIntent = viewModel::onIntent,
            modifier = modifier,
        )
    }
}
