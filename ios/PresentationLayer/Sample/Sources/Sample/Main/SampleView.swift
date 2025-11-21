//
//  Created by Petr Chmelar on 20.05.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import KMPShared
import NavigatorUI
import SwiftUI
import UIToolkit

public struct SampleView: View {

    @StateObject private var viewModel = SampleViewModel()

    public init() {}

    public var body: some View {
        ManagedNavigationStack { navigator in
            ZStack {
                switch viewModel.state.sampleText {
                case let .data(text), let .loading(text):
                    contentView(sampleText: text) {
                        viewModel.onIntent(.onButtonTapped)
                    }
                    .skeleton(viewModel.state.sampleText.isLoading)
                case let .error(error):
                    ErrorView(error: error)
                case .empty:
                    EmptyView()
                }
            }
            .navigationTitle(MR.strings().bottom_bar_item_1.toLocalized())
            .navigationBarTitleDisplayMode(.inline)
            .toastView(Binding<ToastData?>(
                get: { viewModel.state.toast },
                set: { toast in viewModel.onIntent(.onToastChanged(data: toast)) }
            ))
            .task { await bindEvents(navigator: navigator) }
            .lifecycle(viewModel)
        }
    }
    
    private func bindEvents(navigator: Navigator) async {
        for await event in viewModel.events {
            switch event {
            case .showNextScreen: navigator.navigate(to: SampleDestination.next)
            }
        }
    }

    private func contentView(
        sampleText: SampleText,
        onButtonTapped: @escaping () -> Void
    ) -> some View {
        VStack(spacing: AppTheme.Dimens.spaceMedium) {
            Text("This is a sample with SwiftUI and iOS VM")

            Text(sampleText.value)

            Button("Click me!", action: onButtonTapped)
            
            Button("Show next") {
                viewModel.onIntent(.onNextTapped)
            }
        }
    }
}

#if DEBUG
import DependencyInjectionMocks
import Factory

 #Preview {
    let _ = fixMokoResourcesForPreviews()
    let _ = Container.shared.registerUseCaseMocks()

    SampleView()
 }
#endif
