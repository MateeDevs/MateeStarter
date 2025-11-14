package kmp.android.samplefeature.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.android.samplefeature.ui.sampleFeatureMainRoute

fun NavGraphBuilder.sampleFeatureNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        startDestination = SampleFeatureGraph.Main.route,
        route = SampleFeatureGraph.rootPath,
    ) {
        sampleFeatureMainRoute()
    }
}
