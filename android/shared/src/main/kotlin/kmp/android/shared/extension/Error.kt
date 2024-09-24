package kmp.android.shared.extension

import android.annotation.SuppressLint
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kmp.shared.base.ErrorResult
import kotlinx.coroutines.flow.Flow

@SuppressLint("ComposableNaming")
@Suppress("konsist.every internal or public compose function has a modifier")
@Composable
infix fun Flow<ErrorResult>.showIn(snackHost: SnackbarHostState) {
    val context = LocalContext.current
    LaunchedEffect(this, snackHost) {
        collect { error ->
            snackHost.showSnackbar(error.localizedMessage.toString(context))
        }
    }
}
