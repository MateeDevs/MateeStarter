package kmp.shared.samplesharedviewmodel.base.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

@Stable
expect abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent>() : AndroidXViewModel, BaseIntentViewModel<S, I, E> {
    override val state: StateFlow<S>

    @Suppress("VariableNaming")
    protected val _events: MutableSharedFlow<E>
    override val events: SharedFlow<E>

    @Composable
    protected abstract fun getState(): S
}

@Stable
expect interface BaseIntentViewModel<S : VmState, I : VmIntent, E : VmEvent> {
    val state: StateFlow<S>
    val events: SharedFlow<E>

    fun onIntent(intent: I)

    fun onViewAppeared()
}

@Immutable
interface VmState

@Immutable
interface VmIntent

@Immutable
interface VmEvent
