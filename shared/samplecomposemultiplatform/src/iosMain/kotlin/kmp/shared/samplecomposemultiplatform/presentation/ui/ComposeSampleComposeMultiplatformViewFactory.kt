package kmp.shared.samplecomposemultiplatform.presentation.ui

import platform.UIKit.UIViewController

public actual interface ComposeSampleComposeMultiplatformViewFactory {
    public fun createPlatformSpecificCheckboxView(
        text: String,
        checked: Boolean,
        onCheckedChanged: (Boolean) -> Unit,
    ): Pair<UIViewController, PlatformSpecificCheckboxViewDelegate>
}
