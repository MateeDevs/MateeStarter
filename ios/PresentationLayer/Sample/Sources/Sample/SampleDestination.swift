//
//  Created by Tomáš Batěk on 22.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import NavigatorUI
import SwiftUI

enum SampleDestination {
    case sample
}

extension SampleDestination: NavigationDestination {
    var body: some View {
        switch self {
        case .sample: EmptyView()
        }
    }
}

public extension View {
    func registerSampleNavigationDestinations() -> some View {
        self
            .registerDestination(SampleDestination.self)
//            .registerSubDestinations()
    }
}
