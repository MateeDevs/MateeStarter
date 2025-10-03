package kmp.shared.samplecomposemultiplatform.presentation.ui

interface PlatformSpecificBottomBarDelegate {
    fun updateItems(items: List<String>)
    fun updateSelected(selected: String)
    fun updateOnSelectedChanged(onSelectedChanged: (String) -> Unit)
    fun updateOnSizeChanged(onSizeChanged: (Float, Float) -> Unit)
}