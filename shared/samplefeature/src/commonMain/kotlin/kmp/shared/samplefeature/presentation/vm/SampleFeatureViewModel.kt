package kmp.shared.samplefeature.presentation.vm

import kmp.shared.analytics.domain.model.ToastAnalytics
import kmp.shared.analytics.domain.model.ToastAnalytics.ViewType
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase
import kmp.shared.analytics.domain.usecase.TrackAnalyticsEventUseCase.Params
import kmp.shared.base.domain.model.ErrorResult
import kmp.shared.base.domain.util.extension.alsoOnError
import kmp.shared.base.domain.util.extension.alsoOnSuccess
import kmp.shared.base.presentation.vm.BaseViewModel
import kmp.shared.base.presentation.vm.VmEvent
import kmp.shared.base.presentation.vm.VmIntent
import kmp.shared.base.presentation.vm.VmState
import kmp.shared.samplefeature.domain.model.Joke
import kmp.shared.samplefeature.domain.usecase.GetRandomJokeUseCase

class SampleFeatureViewModel(
    private val getRandomJoke: GetRandomJokeUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase,
) : BaseViewModel<SampleFeatureState, SampleFeatureIntent, SampleFeatureEvent>(SampleFeatureState()) {

    override suspend fun applyIntent(intent: SampleFeatureIntent) {
        when (intent) {
            SampleFeatureIntent.OnAppeared -> loadRandomJoke()
            SampleFeatureIntent.OnButtonTapped -> showToast()
        }
    }

    private suspend fun loadRandomJoke() {
        update { copy(loading = true) }
        getRandomJoke()
            .alsoOnSuccess { joke ->
                update { copy(joke = joke, loading = false) }
            }
            .alsoOnError { error ->
                update { copy(error = error, loading = false) }
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
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface SampleFeatureIntent : VmIntent {
    data object OnAppeared : SampleFeatureIntent
    data object OnButtonTapped : SampleFeatureIntent
}

sealed interface SampleFeatureEvent : VmEvent {
    data class ShowMessage(val message: String) : SampleFeatureEvent
}
