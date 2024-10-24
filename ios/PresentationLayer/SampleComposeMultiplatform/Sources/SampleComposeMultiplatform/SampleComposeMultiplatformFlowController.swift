//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Factory
import KMPShared
import SwiftUI
import UIKit
import UIToolkit

enum SampleComposeMultiplatformFlow: Flow, Equatable {
    case sampleComposeMultiplatform
    case next
}

public final class SampleComposeMultiplatformFlowController: FlowController {
    
    override public func setup() -> UIViewController {
        return BaseHostingController(rootView: SampleComposeMultiplatformView(flowController: self))
    }
    
    override public func handleFlow(_ flow: Flow) {
        guard let sampleComposeMultiplatformFlow = flow as? SampleComposeMultiplatformFlow else { return }
        switch sampleComposeMultiplatformFlow {
        case .sampleComposeMultiplatform: do {}
        case .next: openNext()
        }
    }
    
    private func openNext() {
        let vc = BaseHostingController(rootView: SampleNextView(
            goBack: { self.navigationController.popViewController(animated: true) }
        ))
        navigationController.pushViewController(vc, animated: true)
    }
}
