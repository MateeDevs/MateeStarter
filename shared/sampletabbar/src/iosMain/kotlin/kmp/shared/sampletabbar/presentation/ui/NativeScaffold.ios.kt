package kmp.shared.sampletabbar.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.compose.viewModel
import kmp.shared.samplecomposemultiplatform.presentation.ui.NativeViewHolderViewModel
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSProcessInfo
import platform.UIKit.UIColor
import platform.UIKit.UIViewController
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun NativeScaffold(
    modifier: Modifier,
    toolbar: Toolbar?,
    tabs: List<TabItem>,
    selectedTabPosition: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable (currentTabPosition: Int?, contentPadding: PaddingValues) -> Unit,
) {
    val factory = LocalViewFactory.current
    val key = rememberSaveable { Random.nextInt().toString(16) }

    fun contentMapper(position: Int?): UIViewController =
        ComposeUIViewController(configure = { opaque = false }) {
            CompositionLocalProvider(
                LocalViewFactory provides factory,
            ) {
                SampleTheme {
                    val showBlur = NSProcessInfo.processInfo.operatingSystemVersion.useContents {
                        this.majorVersion >= 26
                    }
                    if (showBlur) {
                        BlurredContainer(
                            top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding(),
                            bottom = WindowInsets.safeContent.asPaddingValues()
                                .calculateBottomPadding(),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            content(position, WindowInsets.safeContent.asPaddingValues())
                        }
                    } else {
                        content(position, WindowInsets.safeContent.asPaddingValues())
                    }
                }
            }
        }

    val viewModel = viewModel(key = key) {
        NativeViewHolderViewModel {
            factory.createNativeScaffold(
                toolbar = toolbar,
                tabs = tabs,
                selectedTabPosition = selectedTabPosition,
                onTabSelected = onTabSelected,
                content = ::contentMapper,
            )
        }
    }
    val delegate = remember(viewModel) { viewModel.delegate }
    val view = remember(viewModel) { viewModel.view }

    remember(toolbar) { delegate.updateToolbar(toolbar) }
    remember(tabs) { delegate.updateTabs(tabs) }
    remember(selectedTabPosition) { delegate.updateSelectedTab(selectedTabPosition) }

    UIKitViewController(
        modifier = modifier,
        factory = { view },
        update = { _ -> },
    )
}

actual class NativeColor actual constructor(private val composeColor: Color) {

    fun toUIColor(): UIColor =
        UIColor.colorWithRed(
            red = composeColor.red.toDouble(),
            green = composeColor.green.toDouble(),
            blue = composeColor.blue.toDouble(),
            alpha = composeColor.alpha.toDouble(),
        )
}
