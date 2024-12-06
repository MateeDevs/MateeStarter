package kmp.shared.samplecomposemultiplatform.presentation.common

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Provides value to ensure the ripple effect looks correctly on Android and is not present on iOS
 */
@Composable
expect fun getPlatformSpecificRippleConfigurationProvidedValue(rippleColor: Color = Color.Unspecified): ProvidedValue<*>

/**
 * Set this as [Indication] for your clickable view to add opacity change effect for iOS
 * Order of modifiers matters: [clickable] should come before [background] for this effect to affect the background as well
 */
expect val platformSpecificClickIndication: Indication

/**
 * Can be added to clickable elements that don't respect [LocalIndication] (such as [Surface])
 * to add the opacity change effect for iOS
 */
@Composable
expect fun Modifier.platformSpecificClickEffect(interactionSource: InteractionSource): Modifier
