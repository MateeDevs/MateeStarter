package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.lifecycle.ViewModel

// From Touchlab's [compose-swift-bridge]
@Suppress("konsist.assertIsDefinedInKoinModule")
class NativeViewHolderViewModel<VIEW_TYPE : Any, DELEGATE : Any>(
    val factory: () -> Pair<VIEW_TYPE, DELEGATE>,
) : ViewModel() {
    private val keep by lazy { factory() }

    val view by lazy { keep.first }
    val delegate by lazy { keep.second }
}
