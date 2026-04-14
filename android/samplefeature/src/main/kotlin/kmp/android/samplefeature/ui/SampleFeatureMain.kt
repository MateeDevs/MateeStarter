package kmp.android.samplefeature.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kmp.shared.samplefeature.presentation.ui.SampleFeatureRoute
import kmp.shared.samplefeature.presentation.vm.SampleFeatureViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SampleFeatureMainRoute(viewModel: SampleFeatureViewModel = koinViewModel()) {
    val context = LocalContext.current

    SampleFeatureRoute(
        viewModel = viewModel,
        onShowMessage = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        },
    )
}
