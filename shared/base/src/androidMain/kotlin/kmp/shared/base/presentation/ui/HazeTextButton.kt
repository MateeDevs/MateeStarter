package kmp.shared.base.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

private val ButtonHeight = 36.dp
private val ButtonShape = RoundedCornerShape(50)
private val OuterPadding = 6.dp

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
internal fun HazeTextButton(
    text: String,
    tint: Color,
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = OuterPadding)
            .height(ButtonHeight)
            .clip(ButtonShape)
            .hazeEffect(
                hazeState,
                style = HazeMaterials.thin(),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = Values.Space.medium),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = tint,
        )
    }
}
