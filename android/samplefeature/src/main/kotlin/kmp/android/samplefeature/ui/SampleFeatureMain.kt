package kmp.android.samplefeature.ui

import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.android.samplefeature.navigation.SampleFeatureGraph
import kmp.android.shared.navigation.composableDestination
import kmp.shared.base.MR
import kmp.shared.samplefeature.presentation.ui.SampleFeatureMainScreen
import kmp.shared.samplefeature.presentation.vm.SampleFeatureEvent
import kmp.shared.samplefeature.presentation.vm.SampleFeatureIntent
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

internal fun NavGraphBuilder.sampleFeatureMainRoute() {
    composableDestination(
        destination = SampleFeatureGraph.Main,
    ) {
        SampleFeatureMainRoute()
    }
}

@Composable
internal fun SampleFeatureMainRoute(
    viewModel: SampleFeatureViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(SampleFeatureIntent.OnAppeared)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SampleFeatureEvent.ShowMessage -> Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(MR.strings.sample_feature_title)) },
                windowInsets = WindowInsets.displayCutout,
            )
        },
    ) { padding ->
        SampleFeatureMainScreen(
            state = state,
            onIntent = { viewModel.onIntent(it) },
            modifier = Modifier.consumeWindowInsets(padding),
        )
    }
}
