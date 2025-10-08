package kmp.shared.sampletabbar.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.ImageResource


@Composable
expect fun NativeScaffold(
    modifier: Modifier = Modifier,
    toolbar: Toolbar? = null,
    tabs: List<TabItem> = emptyList(),
    selectedTabPosition: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    content: @Composable (currentTabPosition: Int?, contentPadding: PaddingValues) -> Unit,
)

data class Toolbar(
    val title: String? = null,
    val buttons: List<ToolbarButtonData> = emptyList(),
)

data class ToolbarButtonData(
    val icon: ImageResource,
    val description: String? = null,
    val position: ToolbarButtonPosition = ToolbarButtonPosition.Trailing,
    val tint: NativeColor? = null,
    val onClick: () -> Unit,
)

expect class NativeColor {
    constructor(composeColor: Color)
}

enum class ToolbarButtonPosition {
    Leading,
    Trailing,
}

data class TabItem(
    val title: String,
    val icon: ImageResource,
    val position: Int,
)