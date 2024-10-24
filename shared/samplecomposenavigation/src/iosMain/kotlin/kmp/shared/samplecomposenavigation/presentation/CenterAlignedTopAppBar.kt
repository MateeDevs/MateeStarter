package kmp.shared.samplecomposenavigation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CenterAlignedTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .safeContentPadding()
            .height(AppBarHeight),
    ) {
        Text(
            title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.onPrimary,
        )

        onBackClick?.let { onBack ->
            TextButton(
                onClick = onBack,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterStart),
                contentPadding = PaddingValues(0.dp),
            ) {
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onPrimary,
                    )
                    Text("Back", color = MaterialTheme.colors.onPrimary)
                }
            }
        }
    }
}

private val AppBarHeight = 32.dp
