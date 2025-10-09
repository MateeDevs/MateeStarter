package kmp.shared.samplecomposemultiplatform.presentation.ui

import dev.icerock.moko.resources.ImageResource
import platform.UIKit.UIViewController

data class BottomBarTabForIos(
    val title: String,
    val icon: ImageResource,
    val position: Int,
    val content: UIViewController,
)
