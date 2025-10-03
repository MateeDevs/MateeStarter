//
//  Created by Julia Jakubcova on 30/09/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class PlatformSpecificBottomBarObservable: ObservableObject, PlatformSpecificBottomBarDelegate {

    @Published var items: [String]
    @Published var selected: String
    @Published var onSelectedChanged: (String) -> Void
    @Published var onSizeChanged: (CGFloat, CGFloat) -> Void

    init(
        items: [String],
        selected: String,
        onSelectedChanged: @escaping (String) -> Void,
        onSizeChanged: @escaping (KotlinFloat, KotlinFloat) -> Void
    ) {
        self.items = items
        self.selected = selected
        self.onSelectedChanged = onSelectedChanged
        self.onSizeChanged = { width, height in
            let scale = UIScreen.main.scale
            onSizeChanged(KotlinFloat(value: Float(width * scale)), KotlinFloat(value: Float(height * scale)))
        }
    }

    func updateItems(items: [String]) {
        self.items = items
    }

    func updateOnSelectedChanged(onSelectedChanged: @escaping (String) -> Void) {
        self.onSelectedChanged = onSelectedChanged
    }

    func updateSelected(selected: String) {
        self.selected = selected
    }

    func updateOnSizeChanged(onSizeChanged: @escaping (KotlinFloat, KotlinFloat) -> Void) {
        self.onSizeChanged = { width, height in
            let scale = UIScreen.main.scale
            onSizeChanged(KotlinFloat(value: Float(width * scale)), KotlinFloat(value: Float(height * scale)))
        }
    }
}
