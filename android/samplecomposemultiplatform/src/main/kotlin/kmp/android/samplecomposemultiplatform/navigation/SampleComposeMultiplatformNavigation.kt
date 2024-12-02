package kmp.android.samplecomposemultiplatform.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.android.samplecomposemultiplatform.ui.navigateToComposeMultiplatformNext
import kmp.android.samplecomposemultiplatform.ui.sampleComposeMultiplatformMainRoute
import kmp.android.samplecomposemultiplatform.ui.sampleComposeMultiplatformNextRoute

fun NavGraphBuilder.sampleComposeMultiplatformNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        startDestination = SampleComposeMultiplatformGraph.Main.route,
        route = SampleComposeMultiplatformGraph.rootPath,
    ) {
        sampleComposeMultiplatformMainRoute(
            navigateToNext = { navHostController.navigateToComposeMultiplatformNext() },
        )

        sampleComposeMultiplatformNextRoute(
            navigateBack = { navHostController.popBackStack() },
        )
    }
}
