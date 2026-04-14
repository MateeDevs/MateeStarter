package kmp.shared.base.presentation.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Velocity

@Composable
actual fun Modifier.dismissKeyboardOnTap(): Modifier {
    val focusManager = LocalFocusManager.current
    return this then Modifier.pointerInput(Unit) {
        awaitEachGesture {
            awaitFirstDown(pass = PointerEventPass.Final)
            val up = waitForUpOrCancellation(pass = PointerEventPass.Final)
            if (up != null) {
                focusManager.clearFocus(force = true)
            }
        }
    }
}

private const val MIN_SCROLL_DELTA = 100f
private const val MIN_SCROLL_FLING = 2000f

@Composable
actual fun Modifier.dismissKeyboardOnScroll(): Modifier {
    val keyboardController = LocalSoftwareKeyboardController.current
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.UserInput && available.y > MIN_SCROLL_DELTA) {
                    keyboardController?.hide()
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > MIN_SCROLL_FLING) {
                    keyboardController?.hide()
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    return this then nestedScroll(nestedScrollConnection)
}

@Composable
actual fun Modifier.focusOnKeyboardShow(focusRequester: FocusRequester): Modifier {
    val isImeVisible by rememberIsImeVisible()
    LaunchedEffect(isImeVisible) {
        if (isImeVisible) {
            focusRequester.requestFocus()
        }
    }
    return this then Modifier.focusRequester(focusRequester)
}

@Composable
private fun rememberIsImeVisible(): State<Boolean> {
    val density = LocalDensity.current
    val ime = WindowInsets.ime

    return remember {
        derivedStateOf {
            ime.getBottom(density) > 0
        }
    }
}
