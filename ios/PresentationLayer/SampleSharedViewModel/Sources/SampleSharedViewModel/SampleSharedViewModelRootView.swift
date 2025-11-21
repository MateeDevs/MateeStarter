//
//  Created by Tomáš Batěk on 21.11.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import NavigatorUI
import SwiftUI
import UIToolkit

public struct SampleSharedViewModelRootView: View {
    
    public init() {}
    
    public var body: some View {
        ManagedNavigationStack { navigator in
            VStack {
                Spacer()
                
                Button(MR.strings().next.toLocalized()) {
                    navigator.navigate(to: SampleSharedViewModelDestination.sample)
                }
                .buttonStyle(.primary(isLarge: false))
                
                Spacer()
            }
            .registerSampleSharedViewModelDestinations()
            .navigationTitle(MR.strings().bottom_bar_item_2.toLocalized())
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}
