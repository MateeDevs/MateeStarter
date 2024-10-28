package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformScreen
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.navigation.SampleComposeNavigationGraph
import kmp.shared.samplecomposenavigation.presentation.navigation.composableDestination
import kmp.shared.samplesharedviewmodel.vm.SampleSharedEvent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedIntent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.sampleComposeNavigationMainRoute(
    onShowMessage: (String) -> Unit,
    navigateToNext: () -> Unit,
) {
    composableDestination(
        destination = SampleComposeNavigationGraph.Main,
    ) {
        SampleComposeNavigationMainRoute(
            onShowMessage = onShowMessage,
            navigateToNext = navigateToNext,
        )
    }
}

@Composable
internal fun SampleComposeNavigationMainRoute(
    onShowMessage: (String) -> Unit,
    navigateToNext: () -> Unit,
) {
    val viewModel: SampleSharedViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(SampleSharedIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                SampleSharedEvent.GoToNext -> navigateToNext()
                is SampleSharedEvent.ShowMessage -> onShowMessage(event.message)
            }
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = "Compose Multiplatform",
                )
            },
        ) {
            SampleComposeMultiplatformScreen(state = state, onIntent = viewModel::onIntent)
        }
    }
}
