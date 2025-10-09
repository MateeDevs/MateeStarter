package kmp.shared.sampletabbar.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NativeScaffold(
    modifier: Modifier,
    toolbar: Toolbar?,
    tabs: List<TabItem>,
    selectedTabPosition: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable (currentTabPosition: Int?, contentPadding: PaddingValues) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            toolbar?.let {
                CenterAlignedTopAppBar(
                    title = {
                        toolbar.title?.let { title ->
                            Text(title)
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    ),
                    navigationIcon = {
                        toolbar.buttons
                            .filter { it.position == ToolbarButtonPosition.Leading }
                            .forEach { button ->
                                IconButton(button.onClick) {
                                    Icon(
                                        painter = painterResource(button.icon.drawableResId),
                                        contentDescription = button.description,
                                        tint = button.tint?.composeColor
                                            ?: LocalContentColor.current,
                                    )
                                }
                            }
                    },
                    actions = {
                        toolbar.buttons
                            .filter { it.position == ToolbarButtonPosition.Trailing }
                            .forEach { button ->
                                IconButton(button.onClick) {
                                    Icon(
                                        painter = painterResource(button.icon.drawableResId),
                                        contentDescription = button.description,
                                        tint = button.tint?.composeColor
                                            ?: LocalContentColor.current,
                                    )
                                }
                            }
                    },
                    modifier = Modifier,
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(visible = tabs.isNotEmpty()) {
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
                            onClick = { onTabSelected(tab.position) },
                            label = {
                                Text(tab.title)
                            },
                            modifier = Modifier.padding(
                                bottom = WindowInsets.systemBars.asPaddingValues()
                                    .calculateBottomPadding(),
                            ),
                        )
                    }
                }
            }
        },
    ) { contentPadding ->
        content(selectedTabPosition.takeIf { tabs.isNotEmpty() }, contentPadding)
    }
}

actual class NativeColor actual constructor(val composeColor: Color)
