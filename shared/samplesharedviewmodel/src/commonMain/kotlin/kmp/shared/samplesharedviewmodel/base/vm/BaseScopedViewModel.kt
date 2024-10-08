package kmp.shared.samplesharedviewmodel.base.vm

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

expect abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent>() :
    AndroidXViewModel, BaseViewModelInt<S, I, E>

expect interface BaseViewModelInt<S : VmState, I : VmIntent, E : VmEvent> {

    val state: StateFlow<S>
    val events: SharedFlow<E>

    fun onIntent(intent: I)
}
