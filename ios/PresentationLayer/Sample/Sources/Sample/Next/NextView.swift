//
//  Created by Tomáš Batěk on 21.11.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import NavigatorUI
import SwiftUI

struct NextView: View {
    
    @Environment(\.navigator) private var navigator
    
    var body: some View {
        VStack {
            Text("Hello there")
            
            Button(MR.strings().back.toLocalized()) {
                navigator.dismiss()
            }
        }
        .presentationDetents([.medium])
    }
}
