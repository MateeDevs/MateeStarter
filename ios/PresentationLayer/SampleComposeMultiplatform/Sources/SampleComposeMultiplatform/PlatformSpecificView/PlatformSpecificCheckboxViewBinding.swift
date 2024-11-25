//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

struct PlatformSpecificCheckboxViewBinding: View {
    @ObservedObject var observable: PlatformSpecificCheckboxViewObservable
    
    init(
        observable: PlatformSpecificCheckboxViewObservable
    ) {
        self.observable = observable
    }
    
    var body: some View {
        CheckboxView(
            text: observable.text,
            isChecked: observable.checked,
            onCheckedChange: { observable.onCheckedChanged(KotlinBoolean(value: $0)) }
        )
    }
}
