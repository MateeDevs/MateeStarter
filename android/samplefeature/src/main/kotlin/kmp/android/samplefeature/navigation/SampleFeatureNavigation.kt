package kmp.android.samplefeature.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kmp.android.samplefeature.ui.SampleFeatureMainRoute
import kmp.android.shared.navigation.Navigator

fun EntryProviderScope<NavKey>.sampleFeatureEntries(
    navigator: Navigator,
) {
  entry<SampleFeatureHome> {
      SampleFeatureMainRoute(
          // Use provided `navigator` to navigate to other screens
      )
  }
}
