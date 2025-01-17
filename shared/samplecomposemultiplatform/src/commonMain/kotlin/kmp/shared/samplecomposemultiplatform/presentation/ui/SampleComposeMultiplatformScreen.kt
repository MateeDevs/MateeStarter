package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp.shared.samplecomposemultiplatform.presentation.common.AppTheme
import kmp.shared.samplecomposemultiplatform.presentation.common.StarterButton
import kmp.shared.samplecomposemultiplatform.presentation.ui.test.TestTags
import kmp.shared.samplecomposemultiplatform.presentation.ui.test.testTag
import kmp.shared.samplesharedviewmodel.vm.SampleSharedIntent
import kmp.shared.samplesharedviewmodel.vm.SampleSharedState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SampleComposeMultiplatformScreen(
    state: SampleSharedState,
    onIntent: (SampleSharedIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = state.loading, label = "AnimatedLoading") { loading ->
            if (loading) {
                CircularProgressIndicator()
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = "This is a sample with compose multiplatform UI and shared VM",
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = state.sampleText?.value ?: "",
                        modifier = Modifier.testTag(TestTags.SampleComposeMultiplatformScreen.SampleText),
                    )

                    var isChecked by remember { mutableStateOf(false) }
                    PlatformSpecificCheckboxView(
                        text = "This is a view implemented in Compose on Android and SwiftUI on iOS",
                        checked = isChecked,
                        onCheckedChanged = { isChecked = it },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                    )

                    StarterButton(onClick = { onIntent(SampleSharedIntent.OnNextButtonTapped) }) {
                        Text(text = "Go to next screen")
                    }
                }
            }
        }
    }
}

// Previews do not work for Fleet version 1.38.89 https://slack-chats.kotlinlang.org/t/22778734/are-there-specific-kotlin-ksp-version-requirements-for-getti
@Preview
@Composable
private fun SampleComposeMultiplatformScreen_Preview() {
    AppTheme {
        SampleComposeMultiplatformScreen(
            state = SampleSharedState(),
            onIntent = {},
        )
    }
}
