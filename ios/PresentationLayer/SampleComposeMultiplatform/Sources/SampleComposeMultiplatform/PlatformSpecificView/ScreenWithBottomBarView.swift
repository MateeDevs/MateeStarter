//
//  Created by Julia Jakubcova on 02/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIToolkit

struct ScreenWithBottomBarView: View {
    @ObservedObject var observable: ScreenWithPlatformSpecificBottomBarObservable

    init(observable: ScreenWithPlatformSpecificBottomBarObservable) {
        self.observable = observable
    }

    var body: some View {
        TabView(selection: $observable.selectedTab) {
            ForEach(observable.tabs, id: \.position) { (tab: BottomBarTabForIos) in
                ComposeViewController {
                    tab.content
                }
                .ignoresSafeArea()
                .tabItem {
                    if let uiImage = tab.icon.toUIImage() {
                        Image(uiImage: uiImage)
                    }
                    Text(tab.title)
                }
                .tag(tab.position)
            }
        }
    }
}
