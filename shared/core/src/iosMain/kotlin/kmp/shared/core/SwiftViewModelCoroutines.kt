package kmp.shared.core

import kmp.shared.samplesharedviewmodel.base.vm.BaseIntentViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun <S : VmState> BaseIntentViewModel<S, *, *>.subscribeToState(
    onEach: (item: S) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job = viewModelScope.launch {
    state
        .onEach {
            onEach(it)
        }
        .catch {
            onThrow(it)
        } // catch{} before onCompletion{} or else completion hits rx first and ends stream
        .onCompletion {
            onComplete()
        }
        .collect()
}

fun <E : VmEvent> BaseIntentViewModel<*, *, E>.subscribeToEvents(
    onEach: (item: E) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job = viewModelScope.launch {
    events
        .onEach {
            onEach(it)
        }
        .catch {
            onThrow(it)
        } // catch{} before onCompletion{} or else completion hits rx first and ends stream
        .onCompletion {
            onComplete()
        }
        .collect()
}
