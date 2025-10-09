package kmp.android

import SampleTabBarScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kmp.android.shared.core.system.BaseActivity
import kmp.shared.sampletabbar.presentation.ui.SampleTheme
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarIntent
import kmp.shared.sampletabbar.presentation.vm.SampleTabBarViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            SampleTheme {
//                Root()
                SampleTabBarMainRoute()
            }
        }
    }
}

@Composable
internal fun SampleTabBarMainRoute(
    viewModel: SampleTabBarViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
        viewModel.onIntent(SampleTabBarIntent.OnAppeared)
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                else -> {}
            }
        }
    }

    SampleTabBarScreen(
        state = state,
        onIntent = viewModel::onIntent,
    )
}
