package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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

        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
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
                    modifier = Modifier.padding(
                        bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
                    ),
                )
            }
        }
    }
}
