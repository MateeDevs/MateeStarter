//
//  Created by Tomáš Batěk on 22.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import NavigatorUI
import SwiftUI

enum SampleSharedViewModelDestination {
    case sample
}

extension SampleSharedViewModelDestination: NavigationDestination {
    var body: some View {
        switch self {
        case .sample: EmptyView()
        }
    }
}

public extension View {
    func registerSampleSharedViewModelDestinations() -> some View {
        self
            .registerDestination(SampleSharedViewModelDestination.self)
    }
}
