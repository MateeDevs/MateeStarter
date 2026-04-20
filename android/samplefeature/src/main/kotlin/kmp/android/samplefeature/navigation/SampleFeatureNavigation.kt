package kmp.android.samplefeature.navigation

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kmp.shared.base.presentation.navigation.Navigator
import kmp.shared.samplefeature.presentation.ui.SampleFeatureRoute

fun EntryProviderScope<NavKey>.sampleFeatureEntries(
    navigator: Navigator,
) {
  entry<SampleFeatureNavKey.Home> {
      val context = LocalContext.current
      SampleFeatureRoute(
          onShowMessage = { message ->
              Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          // Use provided `navigator` to navigate to other screens
      )
  }
}
