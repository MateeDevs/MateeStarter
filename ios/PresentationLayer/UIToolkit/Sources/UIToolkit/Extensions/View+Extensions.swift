//
//  Created by Petr Chmelar on 12.03.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import KMPShared
import NavigatorUI
import SwiftUI

public extension View {
    /// onDismiss modifier. Provided action is called when the View is removed from the hierarchy
    func onDismiss(perform handler: (() -> Void)? = nil) -> some View {
        background {
            OnDismissRepresentable(onDismiss: handler)
                .allowsHitTesting(false)
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

@MainActor
public extension View {
    func bindViewModel<S: VmState & Sendable, I: VmIntent, E: VmEvent & Sendable>(
        _ viewModel: BaseScopedViewModel<S, I, E>
    ) -> some View {
        self
            .modifier(ToolbarBindingModifier(viewModel: viewModel))
            .onDismiss {
                viewModel.clearScope()
            }
    }
}

private struct ToolbarBindingModifier<S: VmState & Sendable, I: VmIntent, E: VmEvent & Sendable>: ViewModifier {
    let viewModel: BaseScopedViewModel<S, I, E>

    @State private var toolbar: Toolbar?

    init(viewModel: BaseScopedViewModel<S, I, E>) {
        self.viewModel = viewModel
        _toolbar = State(initialValue: viewModel.toolbar.value)
    }

    func body(content: Content) -> some View {
        content
            .task {
                for await toolbar in viewModel.toolbar {
                    self.toolbar = toolbar
                }
            }
            .toolbar(toolbar)
    }
}
