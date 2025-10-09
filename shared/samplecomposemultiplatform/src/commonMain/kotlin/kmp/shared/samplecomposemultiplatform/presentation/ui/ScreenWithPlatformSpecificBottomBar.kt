package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.ImageResource

@Composable
expect fun ScreenWithPlatformSpecificBottomBar(
    tabs: List<BottomBarTab>,
    selectedTabPosition: Int,
    onSelectedTabChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
)

data class BottomBarTab(
    val title: String,
    val icon: ImageResource,
    val position: Int,
    val content: @Composable () -> Unit,
)
