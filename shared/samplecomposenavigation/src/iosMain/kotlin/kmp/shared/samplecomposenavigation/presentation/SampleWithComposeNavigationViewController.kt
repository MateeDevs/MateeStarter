package kmp.shared.samplecomposenavigation.presentation

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kmp.shared.samplecomposenavigation.presentation.navigation.SampleComposeNavigationGraph
import kmp.shared.samplecomposenavigation.presentation.navigation.sampleComposeNavigationNavGraph
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleWithComposeNavigationViewController(
    showMessage: (String) -> Unit,
): UIViewController {
    return ComposeUIViewController {
        // Comment out this code if navigation is not available and uncomment the code below
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = SampleComposeNavigationGraph.rootPath,
        ) {
            sampleComposeNavigationNavGraph(
                navHostController = navController,
                onShowMessage = showMessage,
            )
        }

        // View to show if navigation is not available
//        AppTheme {
//            Scaffold(
//                topBar = {
//                    CenterAlignedTopAppBar(
//                        title = "Compose Multiplatform",
//                    )
//                },
//            ) {
//                Text(
//                    text = "Sorry, it seems the compose navigation is commented out. " +
//                            "If you want to try it out, please, uncomment code in SampleWithComposeNavigationViewController.kt, " +
//                            "then uncomment navigation imports in build.gradle.kts of :shared:samplecomposenavigation and change " +
//                            "jetbrains-composePlugin version in libs.versions.toml to 1.7.0 :)",
//                    modifier = Modifier.padding(Values.Space.medium),
//                )
//            }
//        }
    }
}
