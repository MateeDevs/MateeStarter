//
//  Created by Lukáš Matuška on 10.10.2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Foundation
import KMPShared
import SwiftUI

public extension StringResource {
    func toLocalized() -> String {
        self.desc().localized()
    }
}

public extension StringDesc {
    func toLocalized() -> String {
        localized()
    }
}

public extension Image {
    init(_ resource: KMPShared.ImageResource) {
        self.init(resource.assetImageName, bundle: resource.bundle)
    }
}
