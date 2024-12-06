package kmp.shared.samplecomposemultiplatform.presentation.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun getPlatformSpecificRippleConfigurationProvidedValue(rippleColor: Color): ProvidedValue<*> {
    return LocalRippleConfiguration provides null
}

// Use with compose version 1.6.11
//    @Composable
//    actual fun getPlatformSpecificRippleConfigurationProvidedValue(rippleColor: Color): ProvidedValue<*> {
//        val rippleTheme = object : RippleTheme {
//            @Composable
//            override fun defaultColor(): Color = Color.Transparent
//
//            @Composable
//            override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)
//        }
//        return LocalRippleTheme provides rippleTheme
//    }

actual val platformSpecificClickIndication: Indication
    get() = OpacityIndicationNodeFactory

object OpacityIndicationNodeFactory : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return OpacityIndicationNode(interactionSource)
    }

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this
}

private class OpacityIndicationNode(
    private val interactionSource: InteractionSource,
) : Modifier.Node(), DrawModifierNode {
    val animatedAlpha = Animatable(1f)

    private suspend fun animateToPressed() {
        animatedAlpha.animateTo(0.75f, spring())
    }

    private suspend fun animateToResting() {
        animatedAlpha.animateTo(1f, spring())
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToResting()
                    is PressInteraction.Cancel -> animateToResting()
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)
            drawContent()

            drawRect(
                color = Color.White.copy(alpha = animatedAlpha.value),
                blendMode = BlendMode.DstIn,
            )

            restoreToCount(checkPoint)
        }
    }
}

// Use with compose version 1.6.11
//    actual val platformSpecificClickIndication: Indication
//        get() = object : Indication {
//            @Composable
//            override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
//                val isPressed by interactionSource.collectIsPressedAsState()
//                return object : IndicationInstance {
//                    override fun ContentDrawScope.drawIndication() {
//                        with(drawContext.canvas.nativeCanvas) {
//                            val checkPoint = saveLayer(null, null)
//                            drawContent()
//
//                            if (isPressed) {
//                                drawRect(
//                                    color = Color.White.copy(alpha = 0.75f),
//                                    blendMode = BlendMode.DstIn,
//                                )
//                            }
//
//                            restoreToCount(checkPoint)
//                        }
//                    }
//                }
//            }
//        }

@Composable
actual fun Modifier.platformSpecificClickEffect(interactionSource: InteractionSource): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()
    val alpha by animateFloatAsState(if (isPressed) 0.75f else 1f)
    return this then Modifier
        .minimumInteractiveComponentSize()
        .graphicsLayer { this.alpha = alpha }
}
