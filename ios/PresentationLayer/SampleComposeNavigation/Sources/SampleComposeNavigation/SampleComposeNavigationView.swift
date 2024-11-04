//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SwiftUI
import UIToolkit

struct SampleComposeNavigationView: View {

    private weak var flowController: FlowController?
    
    @State private var toastData: ToastData?
    
    init(flowController: FlowController?) {
        self.flowController = flowController
    }
    
    var body: some View {
        ComposeViewController {
            SampleWithComposeNavigationViewControllerKt.SampleWithComposeNavigationViewController(
                showMessage: { message in
                    toastData = ToastData(message, hideAfter: 2)
                }
            )
        }
        .toastView($toastData)
        .navigationBarHidden(true)
        .ignoresSafeArea(edges: [.top])
    }
}
