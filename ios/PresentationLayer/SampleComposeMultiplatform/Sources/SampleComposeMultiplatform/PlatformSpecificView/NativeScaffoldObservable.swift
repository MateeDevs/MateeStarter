//
//  Created by Julia Jakubcova on 30/09/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class NativeScaffoldObservable: ObservableObject, NativeScaffoldDelegate {

    private let onSelectedTabChanged: (Int32) -> Void
    let content: (Int32?) -> UIViewController

    @Published var toolbar: Toolbar?
    @Published var tabs: [TabItem]
    @Published var selectedTab: Int32 {
        didSet {
            if oldValue != selectedTab {
                onSelectedTabChanged(selectedTab)
            }
        }
    }

    init(
        toolbar: Toolbar?,
        tabs: [TabItem],
        selectedTab: Int32,
        onSelectedTabChanged: @escaping (Int32) -> Void,
        content: @escaping (Int32?) -> UIViewController
    ) {
        self.tabs = tabs
        self.selectedTab = selectedTab
        self.onSelectedTabChanged = onSelectedTabChanged
        self.content = content
    }

    func updateToolbar(toolbar: Toolbar?) {
        self.toolbar = toolbar
    }

    func updateTabs(tabs: [TabItem]) {
        self.tabs = tabs
    }

    func updateSelectedTab(selectedTabPosition: Int32) {
        self.selectedTab = selectedTabPosition
    }
}
