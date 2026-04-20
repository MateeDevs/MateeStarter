package kmp.shared.base.presentation.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
actual fun Modifier.dismissKeyboardOnTap(): Modifier = this

@OptIn(ExperimentalLayoutApi::class)
@Composable
actual fun Modifier.dismissKeyboardOnScroll(): Modifier = imeNestedScroll()

@OptIn(ExperimentalLayoutApi::class)
@Composable
actual fun Modifier.focusOnKeyboardShow(
    focusRequester: FocusRequester,
): Modifier {
    val isImeVisible = WindowInsets.isImeVisible
    LaunchedEffect(isImeVisible) {
        if (isImeVisible) {
            focusRequester.requestFocus()
        }
    }
    return this then Modifier.focusRequester(focusRequester)
}
