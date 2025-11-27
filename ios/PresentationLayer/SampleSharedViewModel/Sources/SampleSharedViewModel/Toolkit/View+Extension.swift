//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIToolkit

@MainActor
public extension View {
    @inlinable func bindViewModel<S: VmState & Sendable, I: VmIntent, E: VmEvent & Sendable>(
        _ viewModel: BaseScopedViewModel<S, I, E>,
        state: Binding<S>,
        onEvent: @escaping (E) -> Void
    ) -> some View {
        self
            .task {
                for await value in viewModel.state {
                    state.wrappedValue = value
                }
            }
            .task {
                // Make sure that onViewAppeared will be called after event subcsription
                Task {
                    viewModel.onViewAppeared()
                }
                for await event in viewModel.events {
                    onEvent(event)
                }
            }
            .onDismiss {
                viewModel.clearScope()
            }
    }
}
