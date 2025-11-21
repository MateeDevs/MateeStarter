//
//  Created by Petr Chmelar on 20.05.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SharedDomain
import SharedDomainMocks
import SwiftUI
import UIToolkit

public final class SampleViewModel: UIToolkit.BaseViewModel, ViewModel, ObservableObject {
    
    @Injected(\.getSampleTextUseCase) private(set) var getSampleTextUseCase
    @Injected(\.trackAnalyticsEventUseCase) private(set) var trackAnalyticsEventUseCase
    
    // MARK: Lifecycle
    
    override public func onAppear() {
        super.onAppear()
        executeTask(Task { await loadSampleText() })
    }
    
    // MARK: State
    
    @Published public private(set) var state: State = State()

    public struct State {
        var sampleText: ViewData<SampleText> = .loading(mock: .stub)
        var toast: ToastData?
    }
    
    // MARK: Events
    
    public enum Event: ViewModelEvent {
        case showNextScreen
    }
    
    public var events: AsyncStream<Event> {
        AsyncStream { continuation in
            self._events = continuation
        }
    }
    
    private var _events: AsyncStream<Event>.Continuation?
    
    // MARK: Intent
    
    public enum Intent {
        case onButtonTapped
        case onToastChanged(data: ToastData?)
        case onNextTapped
    }

    public func onIntent(_ intent: Intent) {
        executeTask(Task {
            switch intent {
            case .onButtonTapped: showToast(message: "Button was tapped")
            case .onToastChanged(let data): state.toast = data
            case .onNextTapped: _events?.yield(.showNextScreen)
            }
        })
    }
    
    // MARK: Private
    
    private func loadSampleText() async {
        await execute {
            // Do the business logic
            state.sampleText = .data(try await getSampleTextUseCase.execute())
        } onError: { error in
            // Handle error
            state.sampleText = .error(error)
        } onCancel: { _ in
            // Custom cancel handling
        }
    }
    
    private func showToast(message: String) {
        Task {
            try? await trackAnalyticsEventUseCase.invoke(
                params: TrackAnalyticsEventUseCaseParams(event: ToastAnalytics.ToastPresentedEvent(viewType: .native))
            )
        }
        
        state.toast = ToastData(message, hideAfter: 2)
    }
}
