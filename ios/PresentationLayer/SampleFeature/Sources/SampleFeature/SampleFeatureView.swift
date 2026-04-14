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
    @InjectedObject(\.sampleFeatureViewModel) private var viewModel: SampleFeatureViewModel

    public init() {}

    public var body: some View {
        ManagedNavigationStack { _ in
            ComposeViewController {
                SampleFeatureMainScreenViewController(
                    viewModel: viewModel,
                    onShowMessage: { message in
                        toastData = ToastData(message, hideAfter: 2)
                    }
                )
            }
            .ignoresSafeArea()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .bindViewModel(viewModel)
        }
        .toastView($toastData)
    }
}
