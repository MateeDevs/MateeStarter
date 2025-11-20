package kmp.shared.samplesharedviewmodel.base.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.experimental.ExperimentalObjCName
import androidx.lifecycle.ViewModel as AndroidXViewModel

/**
 * Base class that provides a Kotlin/Native equivalent to the AndroidX `ViewModel`. In particular, this provides
 * a [CoroutineScope][kotlinx.coroutines.CoroutineScope] which uses [Dispatchers.Main][kotlinx.coroutines.Dispatchers.Main]
 * and can be tied into an arbitrary lifecycle by calling [clearScope] at the appropriate time.
 */
@Stable
actual abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent> : AndroidXViewModel(), BaseIntentViewModel<S, I, E> {
    @Composable
    protected actual abstract fun getState(): S

    actual override val state: StateFlow<S> by lazy(LazyThreadSafetyMode.NONE) {
        viewModelScope.launchMolecule(RecompositionMode.Immediate) { getState() }
    }

    @Suppress("VariableNaming")
    protected actual val _events = MutableSharedFlow<E>()
    actual override val events = _events.asSharedFlow()

    /**
     * Cancels the children of the Context of the internal [CoroutineScope][kotlinx.coroutines.CoroutineScope].
     * Can be called, for example, from .onDispose of a SwiftUI View. However, beware, the behaviour would differ from AndroidX view model
     * where the lifecycle is not stopped until the screen is no longer in the backstack
     * (meaning it lives if the user navigates to the next screen, but not when he navigates back)
     */
    override fun clearScope() {
        viewModelScope.coroutineContext.cancelChildren()
        onCleared()
    }
}

@OptIn(ExperimentalObjCName::class)
@Stable
actual interface BaseIntentViewModel<S : VmState, I : VmIntent, E : VmEvent> {
    actual val state: StateFlow<S>
    actual val events: SharedFlow<E>

    actual fun onIntent(
        @ObjCName(swiftName = "_") intent: I,
    )

    actual fun onViewAppeared()

    fun clearScope()
}