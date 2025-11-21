package kmp.android.samplecomposemultiplatform.ui

import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.android.samplecomposemultiplatform.navigation.SampleComposeMultiplatformGraph
import kmp.android.shared.navigation.composableDestination
import kmp.shared.base.MR
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleNextScreen
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextEvent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextIntent
import kmp.shared.samplecomposemultiplatform.presentation.vm.SampleNextViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

internal fun NavController.navigateToComposeMultiplatformNext() {
    navigate(SampleComposeMultiplatformGraph.Next())
}

internal fun NavGraphBuilder.sampleComposeMultiplatformNextRoute(navigateBack: () -> Unit) {
    composableDestination(
        destination = SampleComposeMultiplatformGraph.Next,
    ) {
        SampleComposeMultiplatformNextRoute(navigateBack = navigateBack)
    }
}

@Composable
internal fun SampleComposeMultiplatformNextRoute(
    navigateBack: () -> Unit,
    viewModel: SampleNextViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.onViewAppeared()
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SampleNextEvent.ShowMessage -> Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT,
                ).show()

                SampleNextEvent.NavigateBack -> navigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(MR.strings.next_screen_title)) },
                windowInsets = WindowInsets.displayCutout,
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onIntent(SampleNextIntent.OnBackTapped) },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(MR.strings.back),
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                },
            )
        },
    ) { padding ->
        SampleNextScreen(
            state = state,
            onIntent = { viewModel.onIntent(it) },
            modifier = Modifier.consumeWindowInsets(padding),
        )
    }
}