package kmp.android.test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kmp.android.MainActivity
import kmp.android.screen.onSampleScreen
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

internal class SampleScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testTexts() {
        with(composeTestRule) {
            onSampleScreen {
                checkContentTextVisible()

                checkSampleTextVisible()
            }
        }
    }

    @Test
    fun testButton() {
        with(composeTestRule) {
            onSampleScreen {
                checkButtonClick()
            }
        }
    }

    companion object {
        @JvmStatic
        @BeforeClass
        // can be renamed to describe what it does
        fun beforeTest() {
            runBlocking {
                // here comes code that needs to be executed before the test even starts (e.g. logout)
            }
        }
    }
}