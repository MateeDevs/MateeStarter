package kmp.android.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import kmp.android.extension.hasTestTag
import kmp.android.extension.onNodeWithTag
import kmp.android.extension.ShortDuration
import kmp.android.extension.waitUntilExactlyOneExists
import kmp.shared.samplecomposenavigation.presentation.ui.test.TestTags

internal interface SampleComposeMultiplatformScreen : Screen {
    fun checkContentTextVisible()
    fun checkSampleTextVisible()
    fun checkButtonClick()
}

private class SampleComposeMultiplatformScreenImpl<A : ComponentActivity>(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
) : SampleComposeMultiplatformScreen {

    override fun checkContentTextVisible() {
        with(testRule) {
            waitUntilExactlyOneExists(hasText("This is a sample with compose multiplatform UI and shared VM"), ShortDuration)

            onNodeWithText("This is a sample with compose multiplatform UI and shared VM")
                .assertIsDisplayed()
        }
    }

    override fun checkSampleTextVisible() {
        with(testRule) {
            waitUntilExactlyOneExists(hasTestTag(TestTags.SampleComposeMultiplatformScreen.SampleText), ShortDuration)

            onNodeWithTag(TestTags.SampleComposeMultiplatformScreen.SampleText)
                .assertIsDisplayed()
        }
    }

    override fun checkButtonClick() {
        with(testRule) {
            onNodeWithText("Go to next screen")
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onSampleComposeMultiplatformScreen(
    action: SampleComposeMultiplatformScreen.() -> Unit,
) = onScreen(SampleComposeMultiplatformScreenImpl(this), action)

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.performNavigationToSampleComposeMultiplatform() {
    onNodeWithText("Compose Multiplatform")
        .performClick()
}
