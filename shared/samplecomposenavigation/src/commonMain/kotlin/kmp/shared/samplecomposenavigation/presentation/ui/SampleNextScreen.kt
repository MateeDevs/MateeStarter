package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.ui.test.TestTags
import kmp.shared.samplecomposenavigation.presentation.ui.test.testTag
import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextIntent
import kmp.shared.samplecomposenavigation.presentation.vm.SampleNextState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SampleNextScreen(
    state: SampleNextState,
    onIntent: (SampleNextIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                        text = "This is a SECOND screen of the sample with compose multiplatform UI and shared VM",
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = state.sampleText?.value ?: "",
                        modifier = Modifier.testTag(TestTags.SampleComposeMultiplatformScreen.SampleText),
                    )

                    Button(onClick = { onIntent(SampleNextIntent.OnButtonTapped) }) {
                        Text(text = "Click me!")
                    }
                }
            }
        }
    }
}

// Previews do not work for Fleet version 1.38.89 https://slack-chats.kotlinlang.org/t/22778734/are-there-specific-kotlin-ksp-version-requirements-for-getti
@Preview
@Composable
private fun SampleNextScreen_Preview() {
    AppTheme {
        SampleNextScreen(
            state = SampleNextState(),
            onIntent = {},
        )
    }
}