package kmp.android.extension

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import dev.icerock.moko.resources.StringResource
import kmp.android.shared.style.AppTheme

/**
 * A content setter that automatically sets the theme
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.setThemedContent(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit,
) {
    setContent {
        AppTheme(
            darkTheme = darkTheme ?: isSystemInDarkTheme(),
            content = content,
        )
    }
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.stringResource(
    @StringRes id: Int,
    vararg formatArgs: Any,
): String {
    return activity.getString(id, *formatArgs)
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.stringResource(
    resource: StringResource,
    vararg formatArgs: Any,
): String {
    return activity.getString(resource.resourceId, *formatArgs)
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.pressBack() {
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
}