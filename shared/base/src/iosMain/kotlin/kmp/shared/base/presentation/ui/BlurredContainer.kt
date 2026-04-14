package kmp.shared.base.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun BlurredContainer(
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val hazeState = rememberHazeState()

    Box(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.hazeSource(hazeState)) {
            content()
        }

        Column {
            val backgroundColor = MaterialTheme.colorScheme.background
            AnimatedVisibility(visible = top > 0.dp) {
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(top)
                            .hazeEffect(
                                hazeState,
                                style = HazeMaterials.ultraThin(),
                            ) {
                                progressive = HazeProgressive.verticalGradient(
                                    easing = EaseIn,
                                    startIntensity = 0.3f,
                                    endIntensity = 0f,
                                )
                            },
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -(top.value * 0.4).dp)
                            .scale(scaleX = 1.5f, scaleY = 1f)
                            .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            .height(top)
                            .background(backgroundColor),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(visible = bottom > 0.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(bottom)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    backgroundColor.copy(alpha = 0.7f),
                                ),
                            ),
                        ),
                )
            }
        }
    }
}
