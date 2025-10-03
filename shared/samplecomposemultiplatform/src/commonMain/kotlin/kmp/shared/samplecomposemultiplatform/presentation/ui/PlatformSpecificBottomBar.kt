package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize

@Composable
expect fun PlatformSpecificBottomBar(
    items: List<String>,
    selected: String,
    onSelectedChanged: (String) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier = Modifier,
)

@Composable
expect fun ScreenWithPlatformSpecificBottomBar(
    tabs: Map<String, @Composable () -> Unit>,
    selectedTab: String,
    onSelectedTabChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
)
