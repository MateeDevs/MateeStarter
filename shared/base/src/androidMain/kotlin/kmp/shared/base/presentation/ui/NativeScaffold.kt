package kmp.shared.base.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource as androidPainterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.base.R
import kmp.shared.base.presentation.navigation.LocalNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NativeScaffold(
    modifier: Modifier,
    toolbar: Toolbar?,
    snackbarHost: @Composable (() -> Unit),
    contentWindowInsets: WindowInsets,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    val hazeState = if (toolbar != null && toolbar.anBackgroundColor == null) rememberHazeState() else null
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val toolbarButtons = toolbar?.buttons.orEmpty()
    val (leadingButtons, trailingButtons) = remember(toolbarButtons) {
        toolbarButtons.partition { it.position == ToolbarButtonPosition.Leading }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            toolbar?.let {
                CenterAlignedTopAppBar(
                    title = {
                        toolbar.title?.let { title ->
                            Text(
                                text = stringResource(title),
                                color = toolbar.titleColor?.composeColor ?: MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = toolbar.anBackgroundColor?.composeColor ?: Color.Transparent,
                        scrolledContainerColor = toolbar.anBackgroundColor?.composeColor ?: Color.Transparent,
                        navigationIconContentColor = Color.Unspecified,
                        titleContentColor = Color.Unspecified,
                        actionIconContentColor = Color.Unspecified,
                    ),
                    navigationIcon = {
                        toolbar.headerLogo?.let { logo ->
                            Image(
                                painter = painterResource(logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = Values.Space.mediumLarge)
                                    .height(HEADER_IMAGE_HEIGHT),
                            )
                        }
                        leadingButtons.forEach { button ->
                            when (button) {
                                is ToolbarButtonData.BackButton -> ToolbarBackButton(button, hazeState)
                                is ToolbarButtonData.Button -> ToolbarButton(button, hazeState)
                                is ToolbarButtonData.Menu -> ToolbarMenuButton(button, hazeState)
                            }
                        }
                    },
                    actions = {
                        trailingButtons.forEach { button ->
                            when (button) {
                                is ToolbarButtonData.BackButton -> ToolbarBackButton(button, hazeState)
                                is ToolbarButtonData.Button -> ToolbarButton(button, hazeState)
                                is ToolbarButtonData.Menu -> ToolbarMenuButton(button, hazeState)
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        snackbarHost = snackbarHost,
        contentWindowInsets = contentWindowInsets,
    ) { contentPadding ->
        if (hazeState != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState),
            ) {
                content(contentPadding)
            }
        } else {
            content(contentPadding)
        }
    }
}

@Composable
private fun ToolbarBackButton(
    button: ToolbarButtonData.BackButton,
    hazeState: HazeState?,
) {
    val backContentDescription = stringResource(MR.strings.back)
    val backTint = button.tint?.composeColor ?: LocalContentColor.current
    val navigator = LocalNavigator.current

    if (hazeState != null) {
        HazeIconButton(
            painter = androidPainterResource(R.drawable.ic_back_arrow),
            tint = backTint,
            contentDescription = backContentDescription,
            onClick = navigator::navigateUp,
            hazeState = hazeState,
        )
    } else {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = androidPainterResource(R.drawable.ic_back_arrow),
                contentDescription = backContentDescription,
                tint = backTint,
            )
        }
    }
}

@Composable
private fun ToolbarButton(
    button: ToolbarButtonData.Button,
    hazeState: HazeState?,
) {
    val tint = button.tint?.composeColor

    when  {
        button.icon != null && button.label == null -> {
            if (hazeState != null) {
                HazeIconButton(
                    painter = painterResource(button.icon),
                    tint = tint,
                    onClick = button.onClick,
                    hazeState = hazeState,
                )
            } else {
                IconButton(onClick = button.onClick) {
                    Image(
                        painter = painterResource(button.icon),
                        contentDescription = null,
                        colorFilter = tint?.let { ColorFilter.tint(it) },
                    )
                }
            }
        }

        button.label != null && button.icon == null -> {
            if (hazeState != null) {
                HazeTextButton(
                    text = stringResource(button.label),
                    tint = tint ?: Color.Unspecified,
                    onClick = button.onClick,
                    hazeState = hazeState,
                )
            } else {
                TextButton(onClick = button.onClick) {
                    Text(
                        text = stringResource(button.label),
                        color = tint ?: Color.Unspecified,
                    )
                }
            }
        }

        else -> {
            TextButton(onClick = button.onClick) {
                if (button.icon != null) {
                    Image(
                        painter = painterResource(button.icon),
                        contentDescription = null,
                        colorFilter = tint?.let { ColorFilter.tint(it) },
                    )
                }

                if (button.label != null) {
                    Text(
                        text = stringResource(button.label),
                        color = tint ?: LocalContentColor.current,
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolbarMenuButton(
    menu: ToolbarButtonData.Menu,
    hazeState: HazeState?,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ToolbarButton(
        button = ToolbarButtonData.Button(
            icon = menu.icon,
            label = menu.label,
            position = menu.position,
            tint = menu.tint,
            onClick = { expanded = true },
        ),
        hazeState = hazeState,
    )
    DropdownMenu(expanded, onDismissRequest = { expanded = false }, modifier = modifier) {
        menu.options.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = stringResource(option.label)) },
                onClick = { option.onClick(); expanded = false },
                leadingIcon = option.icon?.let { icon -> { Image(painter = painterResource(icon), contentDescription = null) } },
            )
        }
    }
}

private val HEADER_IMAGE_HEIGHT = 32.dp
