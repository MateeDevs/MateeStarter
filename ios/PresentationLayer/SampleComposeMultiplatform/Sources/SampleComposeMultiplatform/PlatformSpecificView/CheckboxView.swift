//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

struct CheckboxView: View {
    
    @ObservedObject var observable: PlatformSpecificCheckboxViewObservable
    
    init(observable: PlatformSpecificCheckboxViewObservable) {
        self.observable = observable
    }
    
    var body: some View {
        HStack {
            Image(systemSymbol: observable.checked ? .checkmarkSquareFill : .square)
                .foregroundColor(observable.checked ? Color(UIColor.systemBlue) : Color.secondary)
                .onTapGesture {
                    observable.onCheckedChanged(KotlinBoolean(value: !observable.checked))
                }
            
            Text(observable.text)
        }
    }
}
