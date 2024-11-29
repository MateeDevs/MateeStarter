package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

public val LocalSampleComposeMultiplatformViewFactory: ProvidableCompositionLocal<ComposeSampleComposeMultiplatformViewFactory> =
    compositionLocalOf(
        defaultFactory = {
            error(
                """You have to provide LocalSampleComposeMultiplatformViewFactory""",
            )
        },
    )
