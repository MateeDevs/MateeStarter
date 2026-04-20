package kmp.shared.base.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeScaffold(
    modifier: Modifier,
    toolbar: Toolbar?,
    snackbarHost: @Composable (() -> Unit),
    contentWindowInsets: WindowInsets,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.dismissKeyboardOnTap(),
        topBar = {
            // No Compose App bar - using SwiftUI native nav bar
        },
        snackbarHost = snackbarHost,
        contentWindowInsets = contentWindowInsets.exclude(WindowInsets.ime),
    ) { contentPadding ->
        val showBlur = toolbar?.title != null || toolbar?.headerLogo != null

        if (showBlur) {
            BlurredContainer(
                top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.safeContent.asPaddingValues().calculateBottomPadding(),
                modifier = Modifier.fillMaxSize(),
            ) {
                content(contentPadding)
            }
        } else {
            content(contentPadding)
        }
    }
}
