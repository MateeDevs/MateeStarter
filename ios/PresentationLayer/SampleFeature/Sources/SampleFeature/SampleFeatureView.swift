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

public struct SampleFeatureView: View {
    
    @State private var toastData: ToastData?
    @Injected(\.sampleFeatureViewModel) private var viewModel: SampleFeatureViewModel
    
    public init() {}
    
    public var body: some View {
        ManagedNavigationStack { _ in
            ComposeViewController {
                SampleFeatureMainScreenViewController(viewModel: viewModel)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .tint(AppTheme.Colors.navBarTitle) // Back button color
        .toastView($toastData)
        .bindViewModel(viewModel, onEvent: onEvent)
    }
    
    private func onEvent(_ event: SampleFeatureEvent) {
        switch onEnum(of: event) {
        case .showMessage(let data):
            toastData = ToastData(data.message, hideAfter: 2)
        }
    }
}
