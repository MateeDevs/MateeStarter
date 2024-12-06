package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("konsist.every internal or public compose function has a modifier")
@Composable
actual fun PlatformSpecificCheckboxView(
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChanged,
        )

        Text(
            text = text,
            style = MaterialTheme.typography.body1,
        )
    }
}
