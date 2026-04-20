package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import kmp.android.samplefeature.navigation.SampleFeatureNavKey
import kmp.android.samplefeature.navigation.sampleFeatureEntries
import kmp.android.shared.navigation.mateePopTransitionSpec
import kmp.android.shared.navigation.mateePredictivePopTransitionSpec
import kmp.android.shared.navigation.mateeTransitionSpec
import kmp.shared.base.presentation.navigation.LocalNavigator
import kmp.shared.base.presentation.navigation.rememberNavigator

@Composable
fun Root(modifier: Modifier = Modifier) {

    val navigator = rememberNavigator(SampleFeatureNavKey.Home) {
        sampleFeatureEntries(navigator = it)
    }

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Scaffold(
            modifier = modifier,
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavDisplay(
                    entries = navigator.entries,
                    onBack = { navigator.navigateUp() },
                    transitionSpec = mateeTransitionSpec(),
                    popTransitionSpec = mateePopTransitionSpec(),
                    predictivePopTransitionSpec = mateePredictivePopTransitionSpec(),
                )
            }
        }
    }
}
