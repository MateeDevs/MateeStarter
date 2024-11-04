//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Factory
import KMPShared
import SwiftUI
import UIKit
import UIToolkit

enum SampleComposeNavigationFlow: Flow, Equatable {
    case sampleComposeNavigation
}

public final class SampleComposeNavigationFlowController: FlowController {
    
    override public func setup() -> UIViewController {
        return BaseHostingController(rootView: SampleComposeNavigationView(flowController: self))
    }
    
    override public func handleFlow(_ flow: Flow) {
        guard let sampleComposeNavigationFlow = flow as? SampleComposeNavigationFlow else { return }
        switch sampleComposeNavigationFlow {
        case .sampleComposeNavigation: do {}
        }
    }
}
