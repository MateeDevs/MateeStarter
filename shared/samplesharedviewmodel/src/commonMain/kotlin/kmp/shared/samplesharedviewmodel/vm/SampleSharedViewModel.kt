package kmp.shared.samplesharedviewmodel.vm

import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.domain.usecase.GetSampleTextUseCase
import kmp.shared.samplesharedviewmodel.base.vm.BaseViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState

class SampleSharedViewModel(
    private val getSampleText: GetSampleTextUseCase,
) : BaseViewModel<SampleSharedState, SampleSharedIntent, SampleSharedEvent>(SampleSharedState()) {

    override suspend fun applyIntent(intent: SampleSharedIntent) {
        when (intent) {
            SampleSharedIntent.OnAppeared -> loadSampleText()
            SampleSharedIntent.OnButtonTapped -> _events.emit(SampleSharedEvent.ShowMessage("Button was tapped"))
            SampleSharedIntent.OnNextButtonTapped -> _events.emit(SampleSharedEvent.GoToNext)
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

data class SampleSharedState(
    val loading: Boolean = false,
    val sampleText: SampleText? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface SampleSharedIntent : VmIntent {
    data object OnAppeared : SampleSharedIntent
    data object OnButtonTapped : SampleSharedIntent
    data object OnNextButtonTapped : SampleSharedIntent
}

sealed interface SampleSharedEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleSharedEvent
    data object GoToNext : SampleSharedEvent
}
