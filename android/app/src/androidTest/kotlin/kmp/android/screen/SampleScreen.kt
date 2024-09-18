package kmp.android.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import cz.up.customer.android.presentation.extensions.hasTestTag
import cz.up.customer.android.presentation.extensions.onNodeWithTag
import kmp.android.extension.ShortDuration
import kmp.android.extension.waitUntilExactlyOneExists
import kmp.shared.samplecomposemultiplatform.presentation.ui.test.TestTags

internal interface SampleScreen : Screen {
    fun checkContentTextVisible()
    fun checkSampleTextVisible()
    fun checkButtonClick()
}

private class SampleScreenImpl<A : ComponentActivity>(
    private val testRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>,
) : SampleScreen {

    override fun checkContentTextVisible() {
        with(testRule) {
            waitUntilExactlyOneExists(hasText("This is a sample with android compose UI and android VM"), ShortDuration)

            onNodeWithText("This is a sample with android compose UI and android VM")
                .assertIsDisplayed()
        }
    }

    override fun checkSampleTextVisible() {
        with(testRule) {
            waitUntilExactlyOneExists(hasTestTag(TestTags.SampleScreen.SampleText), ShortDuration)

            onNodeWithTag(TestTags.SampleScreen.SampleText)
                .assertIsDisplayed()
        }
    }

    override fun checkButtonClick() {
        with(testRule) {
            onNodeWithText("Click me!")
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onSampleScreen(
    action: SampleScreen.() -> Unit,
) = onScreen(SampleScreenImpl(this), action)
