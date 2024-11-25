//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

class SwiftUISampleComposeMultiplatformViewFactory: SampleComposeMultiplatformViewFactory {
    func createPlatformSpecificCheckboxView(observable: PlatformSpecificCheckboxViewObservable) -> AnyView {
        return AnyView(PlatformSpecificCheckboxViewBinding(observable: observable))
    }
}
