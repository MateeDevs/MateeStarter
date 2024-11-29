//
//  Created by Julia Jakubcova on 29/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class PlatformSpecificCheckboxViewObservable: ObservableObject, PlatformSpecificCheckboxViewDelegate {
    
    @Published var checked: Bool
    @Published var onCheckedChanged: (KotlinBoolean) -> Void
    @Published var text: String
    
    init(checked: Bool, onCheckedChanged: @escaping (KotlinBoolean) -> Void, text: String) {
        self.checked = checked
        self.onCheckedChanged = onCheckedChanged
        self.text = text
    }
    
    func updateChecked(checked: Bool) {
        self.checked = checked
    }
    
    func updateOnCheckedChanged(onCheckedChanged: @escaping (KotlinBoolean) -> Void) {
        self.onCheckedChanged = onCheckedChanged
    }
    
    func updateText(text: String) {
        self.text = text
    }
}
