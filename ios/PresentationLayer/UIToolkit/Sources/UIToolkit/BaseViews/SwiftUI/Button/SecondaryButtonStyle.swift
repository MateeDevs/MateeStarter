//
//  Created by Petr Chmelar on 28.02.2022
//  Copyright © 2022 Matee. All rights reserved.
//

import SwiftUI

public extension ButtonStyle where Self == SecondaryButtonStyle {
    static var secondary: Self {
        self.init()
    }
}

public struct SecondaryButtonStyle: ButtonStyle {
    
    public init() {}
    
    public func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(AppTheme.Fonts.secondaryButton)
            .foregroundColor(AppTheme.Colors.secondaryButtonTitle)
            .padding()
    }
}

#if DEBUG
#Preview {
    Button("Lorem Ipsum") {}
        .buttonStyle(SecondaryButtonStyle())
}
#endif
