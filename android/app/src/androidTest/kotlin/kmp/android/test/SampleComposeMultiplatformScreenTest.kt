package kmp.android.test

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import kmp.android.MainActivity
import kmp.android.screen.onSampleComposeMultiplatformScreen
import kmp.android.screen.performNavigationToSampleComposeMultiplatform
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

internal class SampleComposeMultiplatformScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    // you can specify what should happen before every test
    fun navigateToSampleComposeMultiplatform() {
        with(composeTestRule) {
            performNavigationToSampleComposeMultiplatform()
        }
    }

    @Test
    fun testTexts() {
        with(composeTestRule) {
            onSampleComposeMultiplatformScreen {
                checkContentTextVisible()

                checkSampleTextVisible()
            }
        }
    }

    @Test
    fun testButton() {
        with(composeTestRule) {
            onSampleComposeMultiplatformScreen {
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