package kmp.shared.base.presentation.ui

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
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
