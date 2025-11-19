//
//  Created by Tomáš Batěk on 19.11.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared

public class SharedViewModel<S: VmState, I: VmIntent, E: VmEvent> {
    
    public let viewModel: BaseScopedViewModel<S, I, E>
    
    public init(_ viewModel: BaseScopedViewModel<S, I, E>) {
        self.viewModel = viewModel
    }
    
    public func onIntent(_ intent: I) {
        viewModel.onIntent(intent: intent)
    }
    
    deinit {
        viewModel.clearScope()
    }
}
