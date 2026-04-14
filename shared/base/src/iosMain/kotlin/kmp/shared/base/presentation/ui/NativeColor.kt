package kmp.shared.base.presentation.ui

import platform.UIKit.UIColor

fun NativeColor.toUIColor(): UIColor = UIColor.colorWithRed(
    red = composeColor.red.toDouble(),
    green = composeColor.green.toDouble(),
    blue = composeColor.blue.toDouble(),
    alpha = composeColor.alpha.toDouble(),
)
