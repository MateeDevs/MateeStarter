package kmp.shared.base.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

private val ButtonSize = 36.dp

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
internal fun HazeIconButton(
    painter: Painter,
    tint: Color?,
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick) {
        Box(
            modifier = modifier
                .size(ButtonSize)
                .clip(CircleShape)
                .hazeEffect(
                    hazeState,
                    style = HazeMaterials.thin(),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                colorFilter = tint?.let { ColorFilter.tint(it) },
            )
        }
    }
}
