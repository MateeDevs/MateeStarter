package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.UIKit.UIColor
import platform.UIKit.UIEdgeInsetsZero
import platform.UIKit.setAdditionalSafeAreaInsets
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ScreenWithPlatformSpecificBottomBar(
    tabs: List<BottomBarTab>,
    selectedTabPosition: Int,
    onSelectedTabChanged: (Int) -> Unit,
    modifier: Modifier,
) {
    val factory = LocalSampleComposeMultiplatformViewFactory.current
    val key = rememberSaveable { Random.nextInt().toString(16) }

    val viewModel = viewModel(key = key) {
        NativeViewHolderViewModel {
            factory.createScreenWithPlatformSpecificBottomBar(
                tabs = tabs.map { it.toIosTab(factory) },
                selectedTabPosition = selectedTabPosition,
                onSelectedTabChanged = onSelectedTabChanged,
            )
        }
    }
    val delegate = remember(viewModel) { viewModel.delegate }
    val view = remember(viewModel) { viewModel.view }

    remember(tabs) { delegate.updateTabs(tabs.map { it.toIosTab(factory) }) }
    remember(selectedTabPosition) { delegate.updateSelectedTab(selectedTabPosition) }
    remember(onSelectedTabChanged) { delegate.updateOnSelectedTabChanged(onSelectedTabChanged) }
    UIKitViewController(
        modifier = modifier,
        factory = { view },
        update = { controller ->
            controller.view.backgroundColor = UIColor.clearColor
            controller.view.opaque = false
            controller.setAdditionalSafeAreaInsets(UIEdgeInsetsZero.readValue())
        },
    )
}

private fun BottomBarTab.toIosTab(factory: ComposeSampleComposeMultiplatformViewFactory): BottomBarTabForIos =
    BottomBarTabForIos(
        title = title,
        icon = icon,
        position = position,
        content = ComposeUIViewController {
            CompositionLocalProvider(
                LocalSampleComposeMultiplatformViewFactory provides factory,
            ) {
                content()
            }
        },
    )
