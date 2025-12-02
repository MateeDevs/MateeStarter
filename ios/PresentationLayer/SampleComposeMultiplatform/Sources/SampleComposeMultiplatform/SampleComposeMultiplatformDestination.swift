//
//  Created by Tomáš Batěk on 22.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import NavigatorUI
import SwiftUI

enum SampleComposeMultiplatformDestination {
    case next
}

extension SampleComposeMultiplatformDestination: NavigationDestination {
    var body: some View {
        switch self {
        case .next: SampleNextView()
        }
    }
}
