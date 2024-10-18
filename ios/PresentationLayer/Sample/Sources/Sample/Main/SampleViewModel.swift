//
//  Created by Petr Chmelar on 20.05.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SharedDomain
import SwiftUI
import UIToolkit

final class SampleViewModel: UIToolkit.BaseViewModel, ViewModel, ObservableObject {
    
    // MARK: Dependencies
    private weak var flowController: FlowController?
    
    @Injected(\.getSampleTextUseCase) private(set) var getSampleTextUseCase
    @Injected(\.trackAnalyticsEventUseCase) private(set) var trackAnalyticsEventUseCase

    // MARK: Init
    init(flowController: FlowController?) {
        self.flowController = flowController
        super.init()
    }
    
    // MARK: Lifecycle
    
    override func onAppear() {
        super.onAppear()
        executeTask(Task { await loadSampleText() })
    }
    
    // MARK: State
    
    @Published private(set) var state: State = State()

    struct State {
        var sampleText: ViewData<SampleText> = .loading(mock: .stub)
        var toast: ToastData?
    }
    
    // MARK: Intent
    enum Intent {
        case onButtonTapped
        case onToastChanged(data: ToastData?)
    }

    func onIntent(_ intent: Intent) {
        executeTask(Task {
            switch intent {
            case .onButtonTapped: showToast(message: "Button was tapped")
            case .onToastChanged(let data): state.toast = data
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
                params: TrackAnalyticsEventUseCaseParams(event: ToastAnalytics.Event.shared.presented(type: .native))
            )
        }
        
        state.toast = ToastData(message, hideAfter: 2)
    }
}
