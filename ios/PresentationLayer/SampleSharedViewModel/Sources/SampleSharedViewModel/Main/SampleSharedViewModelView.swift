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

public struct SampleSharedViewModelView: View {
    
    @Injected(\.sampleSharedViewModel) private var viewModel: KMPShared.SampleSharedViewModel
    @State private var state = SampleSharedState()
    
    @State private var toastData: ToastData?
    
    public init() {}
    
    public var body: some View {
        ZStack(alignment: .center) {
            if state.loading {
                PrimaryProgressView()
            } else {
                VStack(spacing: AppTheme.Dimens.spaceMedium) {
                    Text("This is a sample with SwiftUI and shared VM")
                    
                    Text(state.sampleText?.value ?? "")
                    
                    Button("Click me!") {
                        viewModel.onIntent(.OnButtonTapped())
                    }
                }
            }
        }
        .bindViewModel(
            viewModel,
            state: $state,
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
