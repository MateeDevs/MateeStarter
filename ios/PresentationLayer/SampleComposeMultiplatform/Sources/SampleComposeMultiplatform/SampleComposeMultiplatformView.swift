//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SwiftUI
import UIToolkit

struct SampleComposeMultiplatformView: View {
    
    @Injected(\.sampleSharedViewModel) private(set) var viewModel: KMPShared.SampleSharedViewModel
    private weak var flowController: FlowController?
    
    @State private var toastData: ToastData?
    
    init(flowController: FlowController?) {
        self.flowController = flowController
    }
    
    var body: some View {
        ComposeViewController {
            SampleComposeMultiplatformScreenViewControllerKt.SampleComposeMultiplatformScreenViewController(
                viewModel: viewModel,
                onEvent: { event in
                    switch onEnum(of: event) {
                    case .showMessage(let message):
                        toastData = ToastData(message.message, hideAfter: 2)
                    }
                }
            )
        }
        .toastView($toastData)
        .navigationTitle(L10n.bottom_bar_item_3)
    }
}
