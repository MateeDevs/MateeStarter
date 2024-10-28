//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import DependencyInjection
import Factory
import KMPShared
import SwiftUI
import UIToolkit

public struct SampleNextView: View {

    private var goBack: () -> Void
    
    @State private var toastData: ToastData?
    
    public init(goBack: @escaping () -> Void) {
        self.goBack = goBack
    }
    
    public var body: some View {
        // Wrap compose multiplatform view in ZStack for the swipe back to work reliably
        ZStack {
            Color.blue
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            
            ComposeViewController {
                SampleNextScreenViewControllerKt.SampleNextScreenViewController(
                    onEvent: { event in
                        switch onEnum(of: event) {
                        case .showMessage(let message):
                            toastData = ToastData(message.message, hideAfter: 2)
                        case .navigateBack:
                            goBack()
                        }
                    }
                )
            }
        }
        .navigationTitle(MR.strings().next_screen_title.toLocalized())
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .toastView($toastData)
    }
}
