package kmp.shared.samplecomposenavigation.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.common.Values
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun SampleWithComposeNavigationViewController(
    showMessage: (String) -> Unit,
): UIViewController {
    return ComposeUIViewController {
//        val navController = rememberNavController()
//        NavHost(navController, startDestination = "sample") {
//            composable("sample") {
//                SampleComposeMultiplatformView { event ->
//                    when (event) {
//                        SampleSharedEvent.GoToNext -> navController.navigate("next")
//                        is SampleSharedEvent.ShowMessage -> showMessage(event.message)
//                    }
//                }
//            }
//            composable(
//                route = "next",
//                enterTransition = { slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left) },
//                exitTransition = { slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right) },
//            ) {
//                SampleNextView { event ->
//                    when (event) {
//                        is SampleNextEvent.ShowMessage -> showMessage(event.message)
//                        SampleNextEvent.NavigateBack -> navController.popBackStack()
//                    }
//                }
//            }
//        }

        // View to show if navigation is not available
        AppTheme {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = "Compose Multiplatform",
                    )
                },
            ) {
                Text(
                    text = "Sorry, it seems the compose navigation is commented out. " +
                        "If you want to try it out, please, uncomment code in SampleWithComposeNavigationViewController.kt, " +
                        "then uncomment navigation imports in build.gradle.kts of :shared:samplecomposenavigation and change " +
                        "jetbrains-composePlugin version in libs.versions.toml to 1.7.0 :)",
                    modifier = Modifier.padding(Values.Space.medium),
                )
            }
        }
    }
}