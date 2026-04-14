package kmp.shared.base.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun NativeScaffold(
    modifier: Modifier = Modifier,
    toolbar: Toolbar?,
    snackbarHost: @Composable (() -> Unit) = {},
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
)
