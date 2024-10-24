//
//  Created by Lukáš Matuška on 10.10.2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Foundation
import KMPShared

public extension StringResource {
    
    func toLocalized() -> String {
        return self.desc().localized()
    }
}
