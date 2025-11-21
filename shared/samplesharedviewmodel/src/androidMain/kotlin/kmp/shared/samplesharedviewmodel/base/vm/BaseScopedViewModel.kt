package kmp.shared.samplesharedviewmodel.base.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import androidx.lifecycle.ViewModel as AndroidXViewModel

@Stable
actual abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent> : AndroidXViewModel(), BaseIntentViewModel<S, I, E> {
    @Composable
    protected actual abstract fun getState(): S

    @Suppress("VariableNaming")
    protected actual val _events = MutableSharedFlow<E>()
    actual override val events = _events.asSharedFlow()

    actual override val state: StateFlow<S> by lazy(LazyThreadSafetyMode.NONE) {
        viewModelScope.launchMolecule(
            mode = RecompositionMode.ContextClock,
            context = AndroidUiDispatcher.Main,
        ) { getState() }
    }
}

@Stable
actual interface BaseIntentViewModel<S : VmState, I : VmIntent, E : VmEvent> {
    actual val state: StateFlow<S>
    actual val events: SharedFlow<E>

    actual fun onIntent(intent: I)

    actual fun onViewAppeared()
}
