package kmp.android.shared.core.ui.util

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

abstract class PermissionRequest(
    protected open val launcher: ActivityResultLauncher<String>,
    open val granted: State<Boolean>,
) {
    abstract fun requestPermission()
}

@Composable
private fun <T : PermissionRequest> rememberPermissionRequest(
    factory: (
        launcher: ActivityResultLauncher<String>,
        granted: State<Boolean>,
    ) -> T,
): T {
    val granted = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted.value = it },
    )

    return remember { factory(launcher, granted) }
}
