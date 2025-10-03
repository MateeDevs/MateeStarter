//
//  Created by Julia Jakubcova on 30/09/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

struct BottomBarView: View {
    @ObservedObject var observable: PlatformSpecificBottomBarObservable

    init(observable: PlatformSpecificBottomBarObservable) {
        self.observable = observable

        // Transparent tab bar setup
        let appearance = UITabBarAppearance()
        appearance.configureWithTransparentBackground()
        appearance.backgroundColor = .clear
        appearance.backgroundEffect = nil
        appearance.shadowColor = nil
        
        appearance.selectionIndicatorImage = UIImage()
        
        UITabBar.appearance().standardAppearance = appearance
        if #available(iOS 15.0, *) {
            UITabBar.appearance().scrollEdgeAppearance = appearance
        }
    }

    var body: some View {
        TabView(selection: $observable.selected) {
            ForEach(observable.items, id: \.self) { item in
                Color.clear
                    .frame(height: 0)
                    .tabItem {
                        Text(item)
                    }
                    .tag(item)
            }
        }
        .frame(height: 50)
        .background(Color.clear)
        .overlay(
            GeometryReader { geometry in
                Color.clear
                    .onAppear {
                        let scale = UIScreen.main.scale
                        observable.onSizeChanged(geometry.size.width * scale, geometry.size.height * scale)
                    }
                    .onChange(of: geometry.size) { newSize in
                        let scale = UIScreen.main.scale
                        observable.onSizeChanged(newSize.width * scale, newSize.height * scale)
                    }
            }
        )
        .onAppear {
            // This kills the default white system background from HostingController
            let scenes = UIApplication.shared.connectedScenes
            let windowScene = scenes.first as? UIWindowScene
            windowScene?.windows.first?.rootViewController?.view.backgroundColor = .clear
        }
    }
}

class TransparentHostingController<Content: View>: UIHostingController<Content> {
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .clear // make SwiftUI root clear
    }
}

struct BottomOnlyTabBarView: View {

    @ObservedObject var observable: PlatformSpecificBottomBarObservable

    var body: some View {
        UIKitTabBarWrapper(
            items: observable.items,
            selected: observable.selected,
            onSelectedChanged: { item in
                observable.onSelectedChanged(item)
            },
            onSizeChanged: { width, height in
                observable.onSizeChanged(width, height)
            }
        )
        .frame(height: 50) // fixed height for bottom bar
    }
}

struct UIKitTabBarWrapper: UIViewRepresentable {

    let items: [String]
    let selected: String
    let onSelectedChanged: (String) -> Void
    let onSizeChanged: (CGFloat, CGFloat) -> Void

    func makeUIView(context: Context) -> UITabBar {
        let tabBar = UITabBar()
        tabBar.delegate = context.coordinator

        // Create UITabBarItem for each string
        tabBar.items = items.enumerated().map { index, title in
            UITabBarItem(title: title, image: nil, tag: index)
        }

        // Set selected item
        if let selectedIndex = items.firstIndex(of: selected) {
            tabBar.selectedItem = tabBar.items?[selectedIndex]
        }

        // Report initial size
        DispatchQueue.main.async {
            onSizeChanged(tabBar.bounds.width * UIScreen.main.scale,
                          tabBar.bounds.height * UIScreen.main.scale)
        }

        return tabBar
    }

    func updateUIView(_ uiView: UITabBar, context: Context) {
        // Update selected tab if changed
        if let selectedIndex = items.firstIndex(of: selected) {
            uiView.selectedItem = uiView.items?[selectedIndex]
        }

        // Update size whenever layout changes
        DispatchQueue.main.async {
            onSizeChanged(uiView.bounds.width * UIScreen.main.scale,
                          uiView.bounds.height * UIScreen.main.scale)
        }
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, UITabBarDelegate {
        let parent: UIKitTabBarWrapper

        init(_ parent: UIKitTabBarWrapper) {
            self.parent = parent
        }

        func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
            let index = item.tag
            guard index < parent.items.count else { return }
            parent.onSelectedChanged(parent.items[index])
        }
    }
}
