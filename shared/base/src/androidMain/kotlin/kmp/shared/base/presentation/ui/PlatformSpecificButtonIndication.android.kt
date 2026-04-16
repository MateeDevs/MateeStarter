package kmp.shared.base.presentation.ui

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun getPlatformSpecificRippleConfigurationProvidedValue(rippleColor: Color): ProvidedValue<*> =
    LocalRippleConfiguration provides RippleConfiguration(rippleColor)

actual val platformSpecificClickIndication: Indication
    @Composable
    get() = LocalIndication.current

@Composable
actual fun Modifier.platformSpecificClickEffect(interactionSource: InteractionSource): Modifier {
    // Android uses only the ripple, nothing else should be added
    return this
}
