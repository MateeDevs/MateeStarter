package kmp.shared.base.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester

@Composable
expect fun Modifier.dismissKeyboardOnTap(): Modifier

@Composable
expect fun Modifier.dismissKeyboardOnScroll(): Modifier

@Composable
expect fun Modifier.focusOnKeyboardShow(
    focusRequester: FocusRequester = remember { FocusRequester() },
): Modifier
