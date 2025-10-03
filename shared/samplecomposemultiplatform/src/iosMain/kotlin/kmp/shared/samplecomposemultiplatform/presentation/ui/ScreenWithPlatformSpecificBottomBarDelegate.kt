package kmp.shared.samplecomposemultiplatform.presentation.ui

import platform.UIKit.UIViewController

interface ScreenWithPlatformSpecificBottomBarDelegate {
    fun updateTabs(tabs: Map<String, UIViewController>)
    fun updateSelectedTab(selectedTab: String)
    fun updateOnSelectedTabChanged(onSelectedTabChanged: (String) -> Unit)
}