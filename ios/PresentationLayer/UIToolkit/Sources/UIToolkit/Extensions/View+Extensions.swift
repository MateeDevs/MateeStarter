//
//  Created by Petr Chmelar on 12.03.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import NavigatorUI
import SwiftUI

@MainActor
public extension View {
    @inlinable func lifecycle(_ viewModel: BaseViewModel) -> some View {
        self
            .onAppear {
                viewModel.onAppear()
            }
            .onDisappear {
                viewModel.onDisappear()
            }
    }
}

public extension View {
    /// Redact a view with a shimmering effect aka show a skeleton
    /// - Inspiration taken from [Redacted View Modifier](https://www.avanderlee.com/swiftui/redacted-view-modifier/)
    @ViewBuilder
    func skeleton(
        _ condition: @autoclosure () -> Bool,
        duration: Double = 1.5,
        bounce: Bool = false
    ) -> some View {
        redacted(reason: condition() ? .placeholder : [])
            .shimmering(active: condition(), duration: duration, bounce: bounce)
    }
    
    /// Registers the navigation destination and adds a deeplink handler
    func registerDestination<D: NavigationDestination>(
        _ destination: D.Type,
        resumeType: NavigationReceiveResumeType = .auto
    ) -> some View {
        navigationDestination(D.self)
            .onNavigationReceive { (destination: D, navigator) in
                navigator.navigate(to: destination)
                return resumeType
            }
    }
}

public extension View {
    func toastView(_ toastData: Binding<ToastData?>) -> some View {
        modifier(ToastViewModifier(toastData: toastData))
    }
    
    func snack(
        _ snackState: SnackState<InfoErrorSnackVisuals>
    ) -> some View {
        self
            .overlay(
                VStack {
                    Spacer()
                    
                    InfoErrorSnackHost(snackState: snackState)
                        .padding(.bottom, 64)
                }
            )
    }
}
