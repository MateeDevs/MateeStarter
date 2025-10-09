//
//  Created by Julia Jakubcova on 08/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIKit

public extension Color {
    init(kmpColor: KMPShared.NativeColor) {
        self = Color(uiColor: kmpColor.toUIColor())
    }
}
