package kmp.shared.samplesharedviewmodel.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.viewModelScope
import kmp.shared.analytics.domain.model.ToastAnalytics
import kmp.shared.analytics.domain.model.ToastAnalytics.ViewType
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase.Params
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.sample.domain.model.SampleText
import kmp.shared.sample.domain.usecase.GetSampleTextUseCase
import kmp.shared.samplesharedviewmodel.base.vm.BaseScopedViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState
import kotlinx.coroutines.launch

class SampleSharedViewModel(
    private val getSampleText: GetSampleTextUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase,
) : BaseScopedViewModel<SampleSharedState, SampleSharedIntent, SampleSharedEvent>() {

    private var sampleText by mutableStateOf<SampleText?>(null)
    private var loading by mutableStateOf(false)
    private var error by mutableStateOf<ErrorResult?>(null)

    @Composable
    override fun getState(): SampleSharedState {
        return SampleSharedState(
            loading = loading,
            error = error,
            sampleText = sampleText,
        )
    }

    override fun onIntent(intent: SampleSharedIntent) {
        when (intent) {
            SampleSharedIntent.OnButtonTapped -> {
                viewModelScope.launch {
                    showToast()
                }
            }
            SampleSharedIntent.OnNextButtonTapped -> {
                viewModelScope.launch {
                    _events.emit(SampleSharedEvent.GoToNext)
                }
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

    private suspend fun showToast() {
        trackAnalyticsEventUseCase(Params(ToastAnalytics.ToastPresentedEvent(ViewType.SharedVM)))
        _events.emit(SampleSharedEvent.ShowMessage("Button was tapped"))
    }
}

data class SampleSharedState(
    val loading: Boolean = false,
    val sampleText: SampleText? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed class SampleSharedIntent : VmIntent {
    data object OnButtonTapped : SampleSharedIntent()
    data object OnNextButtonTapped : SampleSharedIntent()
}

sealed class SampleSharedEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleSharedEvent()
    data object GoToNext : SampleSharedEvent()
}
