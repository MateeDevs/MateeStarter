package kmp.shared.samplefeature.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmp.shared.base.presentation.ui.AppTheme
import kmp.shared.base.presentation.ui.testTag
import kmp.shared.samplefeature.presentation.ui.test.TestTags
import kmp.shared.samplefeature.presentation.vm.SampleFeatureIntent
import kmp.shared.samplefeature.presentation.vm.SampleFeatureState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SampleFeatureMainScreen(
    state: SampleFeatureState,
    onIntent: (SampleFeatureIntent) -> Unit,
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
                        text = state.joke?.setup ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag(TestTags.SampleFeatureMainScreen.JokeSetupText),
                    )

                    Text(
                        text = state.joke?.punchline ?: "",
                        modifier = Modifier.testTag(TestTags.SampleFeatureMainScreen.JokePunchlineText),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SampleFeatureMainScreen_Preview() {
    AppTheme {
        SampleFeatureMainScreen(
            state = SampleFeatureState(),
            onIntent = {},
        )
    }
}
