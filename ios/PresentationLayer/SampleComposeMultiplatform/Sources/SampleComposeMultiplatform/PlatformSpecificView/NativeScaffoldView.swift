//
//  Created by Julia Jakubcova on 02/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIToolkit

struct NativeScaffoldView: View {
    @ObservedObject var observable: NativeScaffoldObservable
    
    init(observable: NativeScaffoldObservable) {
        self.observable = observable
    }
    
    var body: some View {
        if let toolbar = observable.toolbar {
            if #available(iOS 16.0, *) {
                Group {
                    if !observable.tabs.isEmpty {
                        TabView(selection: $observable.selectedTab) {
                            ForEach(observable.tabs, id: \.position) { tab in
                                Group {
                                    if #available(iOS 26.0, *) {
                                        ComposeViewController { observable.content(tab.position) }
                                            .ignoresSafeArea()
                                    } else {
                                        ComposeViewController { observable.content(tab.position) }
                                    }
                                }
                                .tabItem {
                                    if let uiImage = tab.icon.toUIImage() {
                                        Image(uiImage: uiImage)
                                    }
                                    Text(tab.title)
                                }
                                .tag(tab.position)
                            }
                        }
                    } else {
                        if #available(iOS 26.0, *) {
                            ComposeViewController { observable.content(nil) }
                                .ignoresSafeArea()
                        } else {
                            ComposeViewController { observable.content(nil) }
                        }
                    }
                }.toolbar(toolbar)
            } else {
                // Fallback on earlier versions
            }
        }
    }
}

extension View {
    @available(iOS 16.0, *)
    func toolbar(_ toolbar: Toolbar) -> some View {
        NavigationStack {
            self
                .navigationTitle(toolbar.title ?? "")
                .navigationBarTitleDisplayMode(.inline)
                .toolbarBackground(.hidden, for: .navigationBar)
                .toolbar {
                    let leading = toolbar.buttons.filter { $0.position == .leading }
                    let trailing = toolbar.buttons.filter { $0.position == .trailing }
                    
                    if !leading.isEmpty {
                        ToolbarItemGroup(placement: .topBarLeading) {
                            ForEach(Array(leading.enumerated()), id: \.offset) { _, button in
                                toolbarButtonView(for: button)
                            }
                        }
                    }
                    
                    if !trailing.isEmpty {
                        ToolbarItemGroup(placement: .topBarTrailing) {
                            ForEach(Array(trailing.enumerated()), id: \.offset) { _, button in
                                toolbarButtonView(for: button)
                            }
                        }
                    }
                }
        }
    }
    
    @ViewBuilder
    private func toolbarButtonView(for button: ToolbarButtonData) -> some View {
        Button {
            button.onClick()
        } label: {
            if let uiImage = button.icon.toUIImage() {
                Image(uiImage: uiImage)
                    .renderingMode(.template)
            }
        }
        .tint(button.tint.map { Color(kmpColor: $0) } ?? .primary)
    }
}
