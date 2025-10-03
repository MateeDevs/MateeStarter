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

        let appearance = UITabBarAppearance()
        appearance.configureWithTransparentBackground()
        appearance.backgroundEffect = UIBlurEffect(style: .systemMaterial) // liquid glass
        appearance.backgroundColor = .clear
        appearance.shadowColor = nil
        UITabBar.appearance().standardAppearance = appearance
        if #available(iOS 15.0, *) {
            UITabBar.appearance().scrollEdgeAppearance = appearance
        }
    }

    var body: some View {
        TabView(selection: $observable.selectedTab) {
            ForEach(Array(observable.tabs.keys), id: \.self) { key in
                let parts = key.components(separatedBy: "::")
                let title = parts[0]
                let icon = parts[1]

                ComposeViewController {
                    observable.tabs[key]!
                }
                .ignoresSafeArea() // draw behind tab bar
                .background(Color.clear)
                .onAppear {
                    // Disable scrolling only inside Compose content, not TabView itself
                    if let vc = observable.tabs[key] {
                        disableScrollsInCompose(vc.view)
                    }
                }
                .tabItem {
                    Label(title, systemImage: icon)
                }
                .tag(key)
            }
        }
        .background(Color.clear)
    }

    private func disableScrollsInCompose(_ view: UIView?) {
        guard let view = view else { return }

        func recursiveDisable(_ v: UIView) {
            for subview in v.subviews {
                if let scroll = subview as? UIScrollView {
                    scroll.bounces = false
                    scroll.alwaysBounceVertical = false
                    scroll.isScrollEnabled = false
                } else {
                    recursiveDisable(subview)
                }
            }
        }

        recursiveDisable(view)
    }
}
