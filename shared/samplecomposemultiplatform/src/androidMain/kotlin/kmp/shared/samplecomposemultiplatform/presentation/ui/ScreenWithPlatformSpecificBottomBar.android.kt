package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
actual fun ScreenWithPlatformSpecificBottomBar(
    tabs: List<BottomBarTab>,
    selectedTabPosition: Int,
    onSelectedTabChanged: (Int) -> Unit,
    modifier: Modifier,
) {
    val selectedTab = tabs.firstOrNull { it.position == selectedTabPosition }
    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1f)) {
            selectedTab?.content?.invoke()
        }

        BottomNavigation(
            modifier = Modifier.fillMaxWidth(),
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    selected = selectedTabPosition == tab.position,
                    icon = {
                        Icon(
                            painter = painterResource(tab.icon.drawableResId),
                            contentDescription = tab.title,
                        )
                    },
                    onClick = { onSelectedTabChanged(tab.position) },
                    label = {
                        Text(tab.title)
                    },
                )
            }
        }
    }
}