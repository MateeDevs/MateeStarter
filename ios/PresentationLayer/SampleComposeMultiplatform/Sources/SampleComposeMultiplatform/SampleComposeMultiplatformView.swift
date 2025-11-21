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

public struct SampleComposeMultiplatformView: View {
    
    @State private var toastData: ToastData?
    
    public init() {}
    
    public var body: some View {
        ManagedNavigationStack { navigator in
            ComposeViewController {
                SampleComposeMultiplatformScreenViewController(
                    onEvent: { event in
                        switch onEnum(of: event) {
                        case .showMessage(let message):
                            toastData = ToastData(message.message, hideAfter: 2)
                        case .goToNext:
                            navigator.navigate(to: SampleComposeMultiplatformDestination.next)
                        }
                    },
                    factory: SwiftUISampleComposeMultiplatformViewFactory()
                )
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .toastView($toastData)
            .navigationTitle(MR.strings().bottom_bar_item_3.toLocalized())
            .navigationBarTitleDisplayMode(.inline)
        }
        .tint(AppTheme.Colors.navBarTitle) // Back button color
    }
}
