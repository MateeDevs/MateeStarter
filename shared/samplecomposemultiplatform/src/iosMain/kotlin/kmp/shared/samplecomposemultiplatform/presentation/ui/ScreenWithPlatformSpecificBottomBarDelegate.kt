package kmp.shared.samplecomposemultiplatform.presentation.ui

import platform.UIKit.UIViewController

interface ScreenWithPlatformSpecificBottomBarDelegate {
    fun updateTabs(tabs: List<BottomBarTabForIos>)
    fun updateSelectedTab(selectedTabPosition: Int)
    fun updateOnSelectedTabChanged(onSelectedTabChanged: (Int) -> Unit)
}