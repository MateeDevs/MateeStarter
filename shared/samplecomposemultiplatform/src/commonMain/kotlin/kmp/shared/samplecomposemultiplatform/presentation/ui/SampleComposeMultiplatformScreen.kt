package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
//                Box(contentAlignment = Alignment.BottomCenter) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.spacedBy(16.dp),
//                        modifier = Modifier
//                            .verticalScroll(rememberScrollState())
//                            .safeContentPadding()
//                            .padding(16.dp),
//                    ) {
//                        repeat(10) {
//                            Text(
//                                text = "This is a sample with compose multiplatform UI and shared VM",
//                                textAlign = TextAlign.Center,
//                            )
//
//                            Text(
//                                text = state.sampleText?.value ?: "",
//                                modifier = Modifier.testTag(TestTags.SampleComposeMultiplatformScreen.SampleText),
//                            )
//
//                            var isChecked by remember { mutableStateOf(false) }
//                            PlatformSpecificCheckboxView(
//                                text = "This is a view implemented in Compose on Android and SwiftUI on iOS",
//                                checked = isChecked,
//                                onCheckedChanged = { isChecked = it },
//                                modifier = Modifier.fillMaxWidth().height(60.dp),
//                            )
//
//                            StarterButton(onClick = { onIntent(SampleSharedIntent.OnNextButtonTapped) }) {
//                                Text(text = "Go to next screen")
//                            }
//                        }
//                    }
//
                var selected by remember { mutableStateOf("one") }
//                    val tabs = listOf("one", "two", "three")
//                    var size by remember { mutableStateOf(DpSize(10.dp, 10.dp)) }
//                    PlatformSpecificBottomBar(
//                        items = tabs,
//                        selected = selected,
//                        onSelectedChanged = { selected = it },
//                        onSizeChanged = { size = it },
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .safeContentPadding()
//                            .size(size),
//                    )
//                }


                val factory = LocalSampleComposeMultiplatformViewFactory.current
                ScreenWithPlatformSpecificBottomBar(
                    tabs = mapOf(
                        "Home::house.fill" to {
                            CompositionLocalProvider(
                                LocalSampleComposeMultiplatformViewFactory provides factory,
                            ) {
                                Content(state, onIntent)
                            }
                        },
                        "Search::magnifyingglass" to {
                            CompositionLocalProvider(
                                LocalSampleComposeMultiplatformViewFactory provides factory,
                            ) {
                                Content(state, onIntent)
                            }
                        },
                        "Profile::person.crop.circle" to {
                            CompositionLocalProvider(
                                LocalSampleComposeMultiplatformViewFactory provides factory,
                            ) {
                                Content(state, onIntent)
                            }
                        },
                    ),
                    selectedTab = selected,
                    onSelectedTabChanged = { selected = it },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun Content(
    state: SampleSharedState,
    onIntent: (SampleSharedIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(rememberScrollState()).padding(16.dp).safeContentPadding(),
    ) {
        repeat(10) {
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
