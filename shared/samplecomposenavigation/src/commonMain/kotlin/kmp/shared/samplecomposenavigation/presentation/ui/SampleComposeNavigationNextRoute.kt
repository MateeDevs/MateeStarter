package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleNextScreen
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextEvent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextIntent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextViewModel
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.navigation.SampleComposeNavigationGraph
import kmp.shared.samplecomposenavigation.presentation.navigation.composableDestination
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavController.navigateToComposeMultiplatformNext() {
    navigate(SampleComposeNavigationGraph.Next())
}

internal fun NavGraphBuilder.sampleComposeMultiplatformNextRoute(
    onShowMessage: (String) -> Unit,
    navigateToBack: () -> Unit,
) {
    composableDestination(
        destination = SampleComposeNavigationGraph.Next,
    ) {
        SampleComposeNavigationNextRoute(
            onShowMessage = onShowMessage,
            navigateToBack = navigateToBack,
        )
    }
}

@Composable
internal fun SampleComposeNavigationNextRoute(
    onShowMessage: (String) -> Unit,
    navigateToBack: () -> Unit,
) {
    val viewModel: SampleNextViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onViewAppeared()
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                SampleNextEvent.NavigateBack -> navigateToBack()
                is SampleNextEvent.ShowMessage -> onShowMessage(event.message)
            }
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = stringResource(MR.strings.next_screen_title),
                    onBackClick = { viewModel.onIntent(SampleNextIntent.OnBackTapped) },
                )
            },
        ) {
            SampleNextScreen(state = state, onIntent = viewModel::onIntent)
        }
    }
}
