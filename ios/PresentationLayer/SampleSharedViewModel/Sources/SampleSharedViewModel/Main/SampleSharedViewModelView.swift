//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import NavigatorUI
import SwiftUI
import UIToolkit

class SharedViewModelClearer {
    
    private let onClear: () -> Void
    
    init(onClear: @escaping () -> Void) {
        self.onClear = onClear
    }
    
    deinit {
        onClear()
    }
}

public struct SampleSharedViewModelRootView: View {
    
    @State private var presented = false
    
    public init() {}
    
    public var body: some View {
        Button("Click me to navigate") {
            presented = true
        }
        .sheet(isPresented: $presented) {
            SampleSharedViewModelView()
        }
    }
}

public struct SampleSharedViewModelView: View {
    
    @State private var viewModel = Container.shared.sampleSharedViewModel()
    @State private var state = SampleSharedState()
    
    @State private var toastData: ToastData?
    
    public init() {}
    
    public var body: some View {
        ManagedNavigationStack { _ in
            ZStack(alignment: .center) {
                if state.loading {
                    PrimaryProgressView()
                } else {
                    VStack(spacing: AppTheme.Dimens.spaceMedium) {
                        Text("This is a sample with SwiftUI and shared VM")
                        
                        Text(state.sampleText?.value ?? "")
                        
                        Button("Click me!") {
                            viewModel.onIntent(SampleSharedIntentOnButtonTapped())
                        }
                    }
                }
            }
            .navigationTitle(MR.strings().bottom_bar_item_2.toLocalized())
            .navigationBarTitleDisplayMode(.inline)
            .onAppear {
                viewModel.onIntent(SampleSharedIntentOnAppeared())
            }
            .registerSampleSharedViewModelDestinations()
            .bindViewModel(
                viewModel.viewModel,
                stateBinding: $state,
                onEvent: { event in
                    switch onEnum(of: event) {
                    case .showMessage(let message):
                        toastData = ToastData(message.message, hideAfter: 2)
                    case .goToNext:
                        print("Should navigate to next screen")
                    }
                }
            )
            .toastView($toastData)
        }
    }
}

#if DEBUG
import DependencyInjectionMocks
import Factory

 #Preview {
    let _ = fixMokoResourcesForPreviews()
    let _ = Container.shared.registerUseCaseMocks()
    let _ = Container.shared.registerViewModelMocks()
    
    SampleSharedViewModelView()
 }
#endif
