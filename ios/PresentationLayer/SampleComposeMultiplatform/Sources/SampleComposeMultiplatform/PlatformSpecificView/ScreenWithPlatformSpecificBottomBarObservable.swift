//
//  Created by Julia Jakubcova on 30/09/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class ScreenWithPlatformSpecificBottomBarObservable: NSObject, ObservableObject, ScreenWithPlatformSpecificBottomBarDelegate, UITabBarDelegate {

    @Published var tabs: [String: UIViewController]
    @Published var selectedTab: String
    @Published var onSelectedTabChanged: (String) -> Void

    init(
        tabs: [String: UIViewController],
        selectedTab: String,
        onSelectedTabChanged: @escaping (String) -> Void
    ) {
        self.tabs = tabs
        self.selectedTab = selectedTab
        self.onSelectedTabChanged = onSelectedTabChanged
    }

    func updateTabs(tabs: [String: UIViewController]) {
        self.tabs = tabs
    }

    func updateOnSelectedTabChanged(onSelectedTabChanged: @escaping (String) -> Void) {
        self.onSelectedTabChanged = onSelectedTabChanged
    }

    func updateSelectedTab(selectedTab: String) {
        self.selectedTab = selectedTab
    }
    
    func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        let index = item.tag
        let key = Array(tabs.keys)[index]
        selectedTab = key
        onSelectedTabChanged(key)
    }
}
