package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class)
@Composable
public actual fun PlatformSpecificCheckboxView(
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier,
) {
    val factory = LocalSampleComposeMultiplatformViewFactory.current
    val key = rememberSaveable { Random.nextInt().toString(16) }

    val viewModel = viewModel(key = key) {
        NativeViewHolderViewModel(
            { factory.createPlatformSpecificCheckboxView(text, checked, onCheckedChanged) },
        )
    }
    val delegate = remember(viewModel) { viewModel.delegate }
    val view = remember(viewModel) { viewModel.view }

    remember(text) { delegate.updateText(text) }
    remember(checked) { delegate.updateChecked(checked) }
    remember(onCheckedChanged) { delegate.updateOnCheckedChanged(onCheckedChanged) }
    UIKitViewController(
        modifier = modifier,
        factory = { view },
    )
}
