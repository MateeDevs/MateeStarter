package kmp.shared.samplecomposenavigation.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.shared.samplecomposenavigation.presentation.ui.navigateToComposeMultiplatformNext
import kmp.shared.samplecomposenavigation.presentation.ui.sampleComposeMultiplatformNextRoute
import kmp.shared.samplecomposenavigation.presentation.ui.sampleComposeNavigationMainRoute

fun NavGraphBuilder.sampleComposeNavigationNavGraph(
    navHostController: NavHostController,
    onShowMessage: (String) -> Unit,
) {
    navigation(
        startDestination = SampleComposeNavigationGraph.Main.route,
        route = SampleComposeNavigationGraph.rootPath,
    ) {
        sampleComposeNavigationMainRoute(
            onShowMessage = onShowMessage,
            navigateToNext = { navHostController.navigateToComposeMultiplatformNext() },
        )

        sampleComposeMultiplatformNextRoute(
            onShowMessage = onShowMessage,
            navigateToBack = { navHostController.popBackStack() },
        )
    }
}
