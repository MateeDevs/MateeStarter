//
//  Created by Lukáš Matuška on 20.09.2024
//  Copyright © 2024 Matee. All rights reserved.
//

import SwiftUI

public struct ErrorView: View {
    
    let error: Error
    
    public init(error: Error) {
        self.error = error
    }
    
    public var body: some View {
        Text(error.localizedDescription)
    }
}

#Preview {
    ErrorView(error: NSError(domain: "com.example", code: 0, userInfo: nil))
}
