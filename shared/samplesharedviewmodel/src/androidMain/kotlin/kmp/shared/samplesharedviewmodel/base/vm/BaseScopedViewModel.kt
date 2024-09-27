package kmp.shared.samplesharedviewmodel.base.vm

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.viewModelScope as androidXViewModelScope

actual abstract class BaseScopedViewModel<S : VmState, I : VmIntent, E : VmEvent> actual constructor() :
    AndroidXViewModel(), BaseViewModelInt<S, I, E> {

    actual override val viewModelScope: CoroutineScope = androidXViewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}
