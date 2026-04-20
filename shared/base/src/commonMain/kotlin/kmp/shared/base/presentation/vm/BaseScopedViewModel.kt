package kmp.shared.base.presentation.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kmp.shared.base.presentation.ui.Toolbar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

@Stable
expect abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent>() : AndroidXViewModel, BaseIntentViewModel<S, I, E> {
    override val state: StateFlow<S>

    override val toolbar: StateFlow<Toolbar?>

    @Suppress("VariableNaming")
    protected val _events: MutableSharedFlow<E>
    override val events: SharedFlow<E>

    @Composable
    protected abstract fun getState(): S

    @Composable
    protected open fun getToolbar(): Toolbar?
}

@Stable
expect interface BaseIntentViewModel<S : VmState, I : VmIntent, E : VmEvent> {
    val state: StateFlow<S>
    val events: SharedFlow<E>

    val toolbar: StateFlow<Toolbar?>

    fun onIntent(intent: I)

    fun onViewAppeared()
}

@Immutable
interface VmState

@Immutable
interface VmIntent

@Immutable
interface VmEvent
