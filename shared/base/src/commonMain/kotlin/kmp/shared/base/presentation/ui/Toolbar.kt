package kmp.shared.base.presentation.ui

import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

data class Toolbar(
    val title: StringResource? = null,
    val buttons: List<ToolbarButtonData> = emptyList(),
    val headerLogo: ImageResource? = null,
    val isTransparent: Boolean = false,
    val titleColor: NativeColor? = null,
    val backgroundColor: NativeColor? = null,
)

sealed class ToolbarButtonData(
    open val label: StringResource?,
    open val icon: ImageResource?,
    open val position: ToolbarButtonPosition,
    open val tint: NativeColor?,
) {

    data class Button(
        override val label: StringResource?,
        override val icon: ImageResource?,
        override val position: ToolbarButtonPosition = ToolbarButtonPosition.Trailing,
        override val tint: NativeColor? = null,
        val onClick: () -> Unit,
        val isBackButton: Boolean = false,
    ) : ToolbarButtonData(
        label = label,
        icon = icon,
        position = position,
        tint = tint,
    ) {
        companion object {
            fun backButton(onClick: () -> Unit) = Button(
                label = null,
                icon = null,
                position = ToolbarButtonPosition.Leading,
                onClick = onClick,
                isBackButton = true,
            )
        }
    }

    data class Menu(
        override val label: StringResource?,
        override val icon: ImageResource?,
        override val position: ToolbarButtonPosition = ToolbarButtonPosition.Trailing,
        override val tint: NativeColor? = null,
        val options: List<Option>,
    ) : ToolbarButtonData(
        label = label,
        icon = icon,
        position = position,
        tint = tint,
    ) {
        data class Option(
            val label: StringResource,
            val icon: ImageResource?,
            val onClick: () -> Unit,
        )
    }
}

data class NativeColor(val composeColor: Color)

enum class ToolbarButtonPosition {
    Leading,
    Trailing,
}
