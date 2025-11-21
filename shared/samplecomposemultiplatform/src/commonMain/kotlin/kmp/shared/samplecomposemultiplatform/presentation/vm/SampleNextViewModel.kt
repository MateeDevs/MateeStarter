package kmp.shared.samplecomposemultiplatform.presentation.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.domain.usecase.GetSampleTextUseCase
import kmp.shared.samplesharedviewmodel.base.vm.BaseScopedViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState
import kotlinx.coroutines.launch

class SampleNextViewModel(
    private val getSampleText: GetSampleTextUseCase,
) : BaseScopedViewModel<SampleNextState, SampleNextIntent, SampleNextEvent>() {

    private var loading by mutableStateOf(false)
    private var sampleText by mutableStateOf<SampleText?>(null)
    private var error by mutableStateOf<ErrorResult?>(null)

    @Composable
    override fun getState(): SampleNextState {
        return SampleNextState(
            loading = loading,
            sampleText = sampleText,
            error = error,
        )
    }

    override fun onIntent(intent: SampleNextIntent) {
        when (intent) {
            SampleNextIntent.OnButtonTapped -> viewModelScope.launch {
                _events.emit(SampleNextEvent.ShowMessage("Button was tapped"))
            }

            SampleNextIntent.OnBackTapped -> viewModelScope.launch {
                _events.emit(SampleNextEvent.NavigateBack)
            }
        }
    }

    override fun onViewAppeared() {
        viewModelScope.launch {
            loadSampleText()
        }
    }

    private suspend fun loadSampleText() {
        loading = true
        when (val result = getSampleText()) {
            is Result.Success -> Snapshot.withMutableSnapshot {
                sampleText = result.data
                loading = false
            }
            is Result.Error -> Snapshot.withMutableSnapshot {
                error = result.error
                loading = false
            }
        }
    }
}

data class SampleNextState(
    val loading: Boolean = false,
    val sampleText: SampleText? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed class SampleNextIntent : VmIntent {
    data object OnButtonTapped : SampleNextIntent()
    data object OnBackTapped : SampleNextIntent()
}

sealed class SampleNextEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleNextEvent()
    data object NavigateBack : SampleNextEvent()
}
