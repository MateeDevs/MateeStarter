package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
actual fun PlatformSpecificBottomBar(
    items: List<String>,
    selected: String,
    onSelectedChanged: (String) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier,
) {
    val density = LocalDensity.current
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                with(density) { onSizeChanged(DpSize(it.size.width.toDp(), it.size.height.toDp())) }
            },
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = selected == item,
                icon = {
                    Icon(imageVector = Icons.Default.Check, contentDescription = item)
                },
                onClick = { onSelectedChanged(item) },
                label = {
                    Text(item)
                }
            )
        }
    }
}

@Composable
actual fun ScreenWithPlatformSpecificBottomBar(
    tabs: Map<String, @Composable (() -> Unit)>,
    selectedTab: String,
    onSelectedTabChanged: (String) -> Unit,
    modifier: Modifier,
) {
}