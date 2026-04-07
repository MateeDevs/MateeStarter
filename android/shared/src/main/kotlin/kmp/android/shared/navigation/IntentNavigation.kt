package kmp.android.shared.navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import co.touchlab.kermit.Logger

fun navigateToURL(
    context: Context,
    url: String,
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url.toUri()
        context.startActivity(intent, null)
    } catch (e: Exception) {
        Logger.e(e.message ?: "Failed to open in browser")
    }
}