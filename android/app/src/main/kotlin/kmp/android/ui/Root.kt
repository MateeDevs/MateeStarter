package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kmp.android.samplefeature.navigation.SampleFeatureGraph
import kmp.android.samplefeature.navigation.sampleFeatureNavGraph

@Composable
fun Root(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController,
                startDestination = SampleFeatureGraph.rootPath,
            ) {
                sampleFeatureNavGraph(navController)
            }
        }
    }
}
