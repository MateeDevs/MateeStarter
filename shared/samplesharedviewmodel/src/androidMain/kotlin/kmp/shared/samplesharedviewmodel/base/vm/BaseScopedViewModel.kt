package kmp.shared.samplesharedviewmodel.base.vm

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

actual abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent> actual constructor() :
    AndroidXViewModel(), BaseViewModelInt<S, I, E>

actual interface BaseViewModelInt<S : VmState, I : VmIntent, E : VmEvent> {

    actual val state: StateFlow<S>
    actual val events: SharedFlow<E>

    actual fun onIntent(intent: I)
}
