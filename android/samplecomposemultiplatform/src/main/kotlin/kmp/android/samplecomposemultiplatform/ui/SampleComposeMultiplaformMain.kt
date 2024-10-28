package kmp.android.samplecomposemultiplatform.ui

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
import kmp.android.samplecomposemultiplatform.navigation.SampleComposeMultiplatformGraph
import kmp.android.shared.navigation.composableDestination
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformScreen
import kmp.shared.samplesharedviewmodel.vm.SampleSharedEvent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedIntent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

internal fun NavGraphBuilder.sampleComposeMultiplatformMainRoute(navigateToNext: () -> Unit) {
    composableDestination(
        destination = SampleComposeMultiplatformGraph.Main,
    ) {
        SampleComposeMultiplatformMainRoute(navigateToNext = navigateToNext)
    }
}

@Composable
internal fun SampleComposeMultiplatformMainRoute(
    navigateToNext: () -> Unit,
    viewModel: SampleSharedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(SampleSharedIntent.OnAppeared)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SampleSharedEvent.ShowMessage -> Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT,
                ).show()

                SampleSharedEvent.GoToNext -> navigateToNext()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Compose Multiplatform") },
                windowInsets = WindowInsets.displayCutout,
            )
        },
    ) { padding ->
        SampleComposeMultiplatformScreen(
            state = state,
            onIntent = { viewModel.onIntent(it) },
            modifier = Modifier.consumeWindowInsets(padding),
        )
    }
}
