//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import SwiftUI

struct CheckboxView: View {
    
    private var text: String
    private var isChecked: Bool
    private var onCheckedChange: (Bool) -> Void
    
    init(text: String, isChecked: Bool, onCheckedChange: @escaping (Bool) -> Void) {
        self.text = text
        self.isChecked = isChecked
        self.onCheckedChange = onCheckedChange
    }
    
    var body: some View {
        HStack {
            Image(systemSymbol: isChecked ? .checkmarkSquareFill : .square)
                .foregroundColor(isChecked ? Color(UIColor.systemBlue) : Color.secondary)
                .onTapGesture {
                    onCheckedChange(!isChecked)
                }
            
            Text(text)
        }
    }
}
