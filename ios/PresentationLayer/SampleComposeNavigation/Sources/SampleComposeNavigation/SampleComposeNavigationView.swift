//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import NavigatorUI
import SampleComposeMultiplatform
import SwiftUI
import UIToolkit

public struct SampleComposeNavigationView: View {
    
    @State private var toastData: ToastData?
    
    public init() {}
    
    public var body: some View {
        ComposeViewController {
            SampleWithComposeNavigationViewController(
                showMessage: { message in
                    toastData = ToastData(message, hideAfter: 2)
                },
                factory: SwiftUISampleComposeMultiplatformViewFactory()
            )
        }
        .toastView($toastData)
        .navigationBarHidden(true)
        .ignoresSafeArea(edges: [.top])
    }
}
