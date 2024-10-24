package kmp.shared.samplecomposenavigation.presentation.vm

import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.domain.usecase.GetSampleTextUseCase
import kmp.shared.samplesharedviewmodel.base.vm.BaseViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState

class SampleNextViewModel(
    private val getSampleText: GetSampleTextUseCase,
) : BaseViewModel<SampleNextState, SampleNextIntent, SampleNextEvent>(SampleNextState()) {

    override suspend fun applyIntent(intent: SampleNextIntent) {
        when (intent) {
            SampleNextIntent.OnAppeared -> loadSampleText()
            SampleNextIntent.OnButtonTapped -> _events.emit(SampleNextEvent.ShowMessage("Button was tapped"))
            SampleNextIntent.OnBackTapped -> _events.emit(SampleNextEvent.NavigateBack)
        }
    }

    private suspend fun loadSampleText() {
        update { copy(loading = true) }
        when (val result = getSampleText()) {
            is Result.Success -> update { copy(sampleText = result.data, loading = false) }
            is Result.Error -> update { copy(error = result.error, loading = false) }
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

sealed interface SampleNextIntent : VmIntent {
    data object OnAppeared : SampleNextIntent
    data object OnButtonTapped : SampleNextIntent
    data object OnBackTapped : SampleNextIntent
}

sealed interface SampleNextEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleNextEvent
    data object NavigateBack : SampleNextEvent
}
