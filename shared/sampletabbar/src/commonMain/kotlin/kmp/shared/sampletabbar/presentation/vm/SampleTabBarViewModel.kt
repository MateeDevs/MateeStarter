package kmp.shared.sampletabbar.presentation.vm

import kmp.shared.samplesharedviewmodel.base.vm.BaseViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState

class SampleTabBarViewModel :
    BaseViewModel<SampleTabBarState, SampleTabBarIntent, SampleTabBarEvent>(SampleTabBarState()) {

    override suspend fun applyIntent(intent: SampleTabBarIntent) {
        when (intent) {
            SampleTabBarIntent.OnAppeared -> onAppeared()
            is SampleTabBarIntent.OnTabSelected -> update { copy(selectedTabPosition = intent.index) }
        }
    }

    private suspend fun onAppeared() {
        if (state.value.tabs.isEmpty()) {
            update {
                copy(
                    tabs = SampleTab.entries,
                )
            }
        }
    }
}

data class SampleTabBarState(
    val tabs: List<SampleTab> = emptyList(),
    val selectedTabPosition: Int = 0,
) : VmState

sealed interface SampleTabBarIntent : VmIntent {
    data object OnAppeared : SampleTabBarIntent
    data class OnTabSelected(val index: Int) : SampleTabBarIntent
}

sealed interface SampleTabBarEvent : VmEvent

enum class SampleTab {
    Numbers, Sentences, Images,
}
