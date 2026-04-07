package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kmp.android.samplefeature.navigation.SampleFeatureHome
import kmp.android.samplefeature.navigation.sampleFeatureEntries
import kmp.android.shared.navigation.Navigator
import kmp.android.shared.navigation.mateePopTransitionSpec
import kmp.android.shared.navigation.mateePredictivePopTransitionSpec
import kmp.android.shared.navigation.mateeTransitionSpec

@Composable
fun Root(modifier: Modifier = Modifier) {

    val mainBackStack = rememberNavBackStack(SampleFeatureHome)
    val mainNavEntries = createMainNavEntries(mainBackStack)
    val navigator = remember(mainBackStack) { Navigator(mainBackStack) }

    Scaffold(
        modifier = modifier,
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavDisplay(
                entries = mainNavEntries,
                onBack = { navigator.navigateUp() },
                transitionSpec = mateeTransitionSpec(),
                popTransitionSpec = mateePopTransitionSpec(),
                predictivePopTransitionSpec = mateePredictivePopTransitionSpec(),
            )
        }
    }
}

@Composable
private fun createMainNavEntries(
    backStack: NavBackStack<NavKey>,
): List<NavEntry<NavKey>> {
    val navigator = remember(backStack) { Navigator(backStack) }

    val decorators = listOf<NavEntryDecorator<NavKey>>(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator(),
    )

    val provider = entryProvider {
        sampleFeatureEntries(navigator = navigator,)
    }

    return rememberDecoratedNavEntries(backStack, decorators, provider)
}