//
//  Created by Julia Jakubcova on 30/09/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class ScreenWithPlatformSpecificBottomBarObservable:
    ObservableObject,
    ScreenWithPlatformSpecificBottomBarDelegate {
    
    @Published var tabs: [BottomBarTabForIos]
    @Published var onSelectedTabChanged: (Int32) -> Void
    @Published var selectedTab: Int32 {
        didSet {
            if oldValue != selectedTab {
                onSelectedTabChanged(selectedTab)
            }
        }
    }
    
    init(
        tabs: [BottomBarTabForIos],
        selectedTab: Int32,
        onSelectedTabChanged: @escaping (Int32) -> Void
    ) {
        self.tabs = tabs
        self.selectedTab = selectedTab
        self.onSelectedTabChanged = onSelectedTabChanged
    }
    
    func updateTabs(tabs: [BottomBarTabForIos]) {
        self.tabs = tabs
    }
    
    func updateOnSelectedTabChanged(onSelectedTabChanged: @escaping (KotlinInt) -> Void) {
        self.onSelectedTabChanged = { onSelectedTabChanged(KotlinInt(value: $0)) }
    }
    
    func updateSelectedTab(selectedTabPosition: Int32) {
        self.selectedTab = selectedTabPosition
    }
}
