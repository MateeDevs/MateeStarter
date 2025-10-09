//
//  Created by Julia Jakubcova on 06/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import Factory
import KMPShared
import SwiftUI
import UIToolkit

public struct SampleTabBarView: View {

    private weak var flowController: FlowController?
    
    @State private var toastData: ToastData?
    
    public init(flowController: FlowController?) {
        self.flowController = flowController
    }
    
    public var body: some View {
        ComposeViewController {
            SampleTabBarViewController(
                onEvent: { event in
                    switch onEnum(of: event) {
                    case .else:
                        print("Unknown event: \(event)")
                    }
                },
                factory: SwiftUIViewFactory()
            )
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .ignoresSafeArea()
        .toastView($toastData)
    }
}
