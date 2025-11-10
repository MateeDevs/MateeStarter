//
//  Created by Tomáš Batěk on 22.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import NavigatorUI
import SwiftUI

enum SampleComposeNavigationDestination {
    case sample
}

extension SampleComposeNavigationDestination: NavigationDestination {
    var body: some View {
        switch self {
        case .sample: EmptyView()
        }
    }
}

public extension View {
    func registerSampleComposeNavigationDestinations() -> some View {
        self
            .registerDestination(SampleComposeNavigationDestination.self)
    }
}
