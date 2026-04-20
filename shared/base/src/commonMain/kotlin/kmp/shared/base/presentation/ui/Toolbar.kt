package kmp.shared.base.presentation.ui

import androidx.compose.ui.graphics.Color
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

/**
 * Shared toolbar model consumed by platform-specific scaffold implementations.
 *
 * @property title Localized title shown in the navigation bar.
 * @property buttons Interactive items rendered on the leading or trailing side.
 * @property headerLogo Optional brand image rendered in the toolbar header.
 * @property titleColor Optional title tint used by platform renderers.
 * @property anBackgroundColor Optional Android-only toolbar background color.
 */
data class Toolbar(
    val title: StringResource? = null,
    val buttons: List<ToolbarButtonData> = emptyList(),
    val headerLogo: ImageResource? = null,
    val titleColor: NativeColor? = null,
    val anBackgroundColor: NativeColor? = null,
)

/**
 * Base type for actions rendered inside a [Toolbar].
 *
 * @property label Optional localized text shown for the action.
 * @property icon Optional image shown for the action.
 * @property position Target side of the toolbar.
 * @property tint Optional platform-specific tint for the rendered content.
 */
sealed class ToolbarButtonData(
    open val label: StringResource?,
    open val icon: ImageResource?,
    open val position: ToolbarButtonPosition,
    open val tint: NativeColor?,
) {
    /** Standard navigation back affordance handled by the hosting platform. */
    data class BackButton(
        override val tint: NativeColor? = null,
    ) : ToolbarButtonData(
        label = null,
        icon = null,
        position = ToolbarButtonPosition.Leading,
        tint = tint,
    )

    /** Simple tappable toolbar action. */
    data class Button(
        override val label: StringResource?,
        override val icon: ImageResource?,
        override val position: ToolbarButtonPosition = ToolbarButtonPosition.Trailing,
        override val tint: NativeColor? = null,
        val onClick: () -> Unit,
    ) : ToolbarButtonData(
        label = label,
        icon = icon,
        position = position,
        tint = tint,
    )

    /** Toolbar action that expands into a list of selectable options. */
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
        /** Single selectable row displayed inside a [Menu]. */
        data class Option(
            val label: StringResource,
            val icon: ImageResource?,
            val onClick: () -> Unit,
        )
    }
}

/** Wraps a Compose color for use in shared toolbar models. */
data class NativeColor(val composeColor: Color)

/** Horizontal placement used when rendering toolbar content. */
enum class ToolbarButtonPosition {
    Leading,
    Trailing,
}
