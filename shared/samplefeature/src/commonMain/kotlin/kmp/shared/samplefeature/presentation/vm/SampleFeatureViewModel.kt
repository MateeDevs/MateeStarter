package kmp.shared.samplefeature.presentation.vm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kmp.shared.analytics.domain.model.ToastAnalytics
import kmp.shared.analytics.domain.model.ToastAnalytics.ViewType
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase.Params
import kmp.shared.base.domain.model.ErrorResult
import kmp.shared.base.domain.util.extension.alsoOnError
import kmp.shared.base.domain.util.extension.alsoOnSuccess
import kmp.shared.base.presentation.vm.BaseScopedViewModel
import kmp.shared.base.presentation.vm.VmEvent
import kmp.shared.base.presentation.vm.VmIntent
import kmp.shared.base.presentation.vm.VmState
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.usecase.GetRandomJokeUseCase
import kotlinx.coroutines.launch

class SampleFeatureViewModel(
    private val getRandomJoke: GetRandomJokeUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase,
) : BaseScopedViewModel<SampleFeatureState, SampleFeatureIntent, SampleFeatureEvent>() {

    private var loading: Boolean by mutableStateOf(false)
    private var joke: Joke? by mutableStateOf(null)
    private var error: ErrorResult? by mutableStateOf(null)

    @Composable
    override fun getState(): SampleFeatureState {
        return SampleFeatureState(
            loading = loading,
            joke = joke,
            error = error,
        )
    }

    override fun onIntent(intent: SampleFeatureIntent) {
        viewModelScope.launch {
            when (intent) {
                SampleFeatureIntent.OnButtonTapped -> showToast()
            }
        }
    }

    override fun onViewAppeared() {
        viewModelScope.launch {
            loadRandomJoke()
        }
    }

    private suspend fun loadRandomJoke() {
        loading = true
        getRandomJoke()
            .alsoOnSuccess { joke ->
                this.joke = joke
                loading = false
            }
            .alsoOnError { error ->
                this.error = error
                loading = false
            }
    }

    private suspend fun showToast() {
        trackAnalyticsEventUseCase(Params(ToastAnalytics.ToastPresentedEvent(ViewType.SharedVM)))
        _events.emit(SampleFeatureEvent.ShowMessage("Button was tapped"))
    }
}

data class SampleFeatureState(
    val loading: Boolean = false,
    val joke: Joke? = null,
    val error: ErrorResult? = null,
) : VmState

sealed interface SampleFeatureIntent : VmIntent {
    data object OnButtonTapped : SampleFeatureIntent
}

sealed interface SampleFeatureEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleFeatureEvent
}
