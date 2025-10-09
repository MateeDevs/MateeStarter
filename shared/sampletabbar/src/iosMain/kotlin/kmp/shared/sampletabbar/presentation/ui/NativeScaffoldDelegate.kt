package kmp.shared.sampletabbar.presentation.ui

interface NativeScaffoldDelegate {
    fun updateToolbar(toolbar: Toolbar?)
    fun updateTabs(tabs: List<TabItem>)
    fun updateSelectedTab(selectedTabPosition: Int)
}
