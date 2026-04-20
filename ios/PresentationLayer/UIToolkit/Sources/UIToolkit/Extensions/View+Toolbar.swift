//
//  Created by Lukáš Matuška on 26.01.2026
//  Copyright © 2026 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIKit

public extension View {
    @ViewBuilder
    func toolbar(_ toolbar: Toolbar?) -> some View {
        if let toolbar {
            self
                .navigationTitle(toolbar.title?.toLocalized() ?? "")
                .navigationBarTitleDisplayMode(.inline)
                .navigationBarBackButtonHidden(!toolbar.buttons.contains { $0.isBackButton })
                .navigationBarTitleColor(toolbar.titleColor.map { Color(kmpColor: $0) })
                .toolbar { toolbarContent(toolbar) }
        } else {
            self
        }
    }
    
    @ToolbarContentBuilder
    private func toolbarContent(_ toolbar: Toolbar) -> some ToolbarContent {
        let leading = toolbar.buttons.filter { $0.position == .leading && !$0.isBackButton }
        let trailing = toolbar.buttons.filter { $0.position == .trailing && !$0.isBackButton }

        if !leading.isEmpty {
            ToolbarItemGroup(placement: .topBarLeading) {
                ForEach(Array(leading.enumerated()), id: \.offset) { _, button in
                    switch button {
                    case let button as ToolbarButtonData.Button:
                        ToolbarIconButton(button: button)
                    case let menu as ToolbarButtonData.Menu:
                        ToolbarMenuButton(menu: menu)
                    default:
                        EmptyView()
                    }
                }
            }
        }

        if !trailing.isEmpty {
            ToolbarItemGroup(placement: .topBarTrailing) {
                ForEach(Array(trailing.enumerated()), id: \.offset) { _, button in
                    switch button {
                    case let button as ToolbarButtonData.Button:
                        ToolbarIconButton(button: button)
                    case let menu as ToolbarButtonData.Menu:
                        ToolbarMenuButton(menu: menu)
                    default:
                        EmptyView()
                    }
                }
            }
        }

        if let headerLogo = toolbar.headerLogo {
            if #available(iOS 26, *) {
                ToolbarItem(placement: .topBarLeading) {
                    ToolbarHeaderLogo(icon: headerLogo)
                }
                .sharedBackgroundVisibility(.hidden)
            } else {
                ToolbarItem(placement: .topBarLeading) {
                    ToolbarHeaderLogo(icon: headerLogo)
                }
            }
        }
    }
}

private struct ToolbarIconButton: View {
    let button: ToolbarButtonData.Button

    var body: some View {
        Button {
            button.onClick()
        } label: {
            button.buttonContent
        }
        .tint(button.tint.map { Color(kmpColor: $0) } ?? .primary)
    }
}

private struct ToolbarMenuButton: View {
    let menu: ToolbarButtonData.Menu

    var body: some View {
        Menu {
            ForEach(menu.options, id: \.self) { option in
                Button {
                    option.onClick()
                } label: {
                    Label {
                        Text(option.label.toLocalized())
                    } icon: {
                        if let icon = option.icon {
                            Image(icon)
                        }
                    }
                }
            }
        } label: {
            menu.buttonContent
        }
        .tint(menu.tint.map { Color(kmpColor: $0) } ?? .primary)
    }
}

private struct ToolbarHeaderLogo: View {
    let icon: KMPShared.ImageResource

    var body: some View {
        Image(icon)
            .resizable()
            .scaledToFill()
            .frame(height: 32)
    }
}

private extension Color {
    init(kmpColor: NativeColor) {
        self.init(uiColor: kmpColor.toUIColor())
    }
}

private extension ToolbarButtonData {
    var isBackButton: Bool {
        self is ToolbarButtonData.BackButton
    }

    var buttonContent: some View {
        HStack {
            if let icon {
                Image(icon)
                    .renderingMode(tint == nil ? .original : .template)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
            }

            if let label {
                Text(label.toLocalized())
            }
        }
    }
}
