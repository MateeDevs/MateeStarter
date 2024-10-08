package kmp.shared.samplesharedviewmodel.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clearScope] at the appropriate time.
 */
actual abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent> :
    AndroidXViewModel(), BaseViewModelInt<S, I, E> {

    final override val viewModelScope: CoroutineScope
        get() = (this as ViewModel).viewModelScope

    /**
     * Cancels the children of the Context of the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope].
     * Can be called for example from .onDispose of a SwiftUI View, but beware, the behaviour would differ from AndroidX view model
     * where the lifecycle is not stopped until the screen is no longer in the backstack
     * (meaning it lives if the user navigates to next screen, but not when he navigates back)
     */
    override fun clearScope() {
        viewModelScope.coroutineContext.cancelChildren()
        onCleared()
    }
}

actual interface BaseViewModelInt<S : VmState, I : VmIntent, E : VmEvent> {

    val viewModelScope: CoroutineScope

    actual val state: StateFlow<S>
    actual val events: SharedFlow<E>

    actual fun onIntent(intent: I)
    fun clearScope()
}
