import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.shared.sampletabbar.presentation.ui.LocalViewFactory
import kmp.shared.sampletabbar.presentation.ui.SampleTheme
import kmp.shared.sampletabbar.presentation.ui.ViewFactory
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarEvent
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarIntent
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleTabBarViewController(
    onEvent: (SampleTabBarEvent) -> Unit,
    factory: ViewFactory,
): UIViewController {
    return ComposeUIViewController {
        val viewModel: SampleTabBarViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(viewModel) {
            viewModel.onIntent(SampleTabBarIntent.OnAppeared)
        }

        LaunchedEffect(viewModel) {
            viewModel.events.collectLatest { event ->
                onEvent(event)
            }
        }
        CompositionLocalProvider(
            LocalViewFactory provides factory,
        ) {
            SampleTheme {
                SampleTabBarScreen(
                    state = state,
                    onIntent = viewModel::onIntent,
                    modifier = Modifier,
                )
            }
        }
    }
}
