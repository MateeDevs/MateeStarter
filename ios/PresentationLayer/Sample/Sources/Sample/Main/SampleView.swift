//
//  Created by Petr Chmelar on 20.05.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIToolkit

struct SampleView: View {
    
    @ObservedObject private var viewModel: SampleViewModel
    
    init(viewModel: SampleViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        Group {
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
        .toastView(Binding<ToastData?>(
            get: { viewModel.state.toast },
            set: { toast in viewModel.onIntent(.onToastChanged(data: toast)) }
        ))
        .lifecycle(viewModel)
    }
    
    private func contentView(
        sampleText: SampleText,
        onButtonTapped: @escaping () -> Void
    ) -> some View {
        VStack(spacing: AppTheme.Dimens.spaceMedium) {
            Text("This is a sample with SwiftUI and iOS VM")
            
            Text(sampleText.value)
            
            Button("Click me!", action: onButtonTapped)
        }
    }
}

#if DEBUG
import DependencyInjectionMocks
import Factory

#Preview {
    Container.shared.registerUseCaseMocks()
    
    let vm = SampleViewModel(flowController: nil)
    return SampleView(viewModel: vm)
}
#endif
