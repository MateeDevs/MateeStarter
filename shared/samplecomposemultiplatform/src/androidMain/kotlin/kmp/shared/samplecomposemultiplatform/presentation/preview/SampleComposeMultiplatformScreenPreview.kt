package kmp.shared.samplecomposemultiplatform.presentation.preview

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import kmp.shared.samplecomposemultiplatform.presentation.common.AppTheme
import kmp.shared.samplecomposemultiplatform.presentation.ui.SampleComposeMultiplatformScreen
import kmp.shared.samplesharedviewmodel.vm.SampleSharedState

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun SampleComposeMultiplatformScreenPreview() {
    AppTheme {
        Surface {
            SampleComposeMultiplatformScreen(
                state = SampleSharedState(loading = false),
                onIntent = {},
            )
        }
    }
}