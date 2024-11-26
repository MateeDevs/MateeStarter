package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.touchlab.compose.swift.bridge.ExpectSwiftView

@ExpectSwiftView(
    factoryName = "SampleComposeMultiplatformView",
    keepStateCrossNavigation = true,
)
@Composable
expect fun PlatformSpecificCheckboxView(
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
)
