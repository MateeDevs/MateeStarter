//
//  Created by Petr Chmelar on 08.03.2021
//  Copyright © 2021 Matee. All rights reserved.
//

import Foundation

public enum ApiFlavor {
    case alpha
    case production
}

public enum BuildVariant {
    case debug
    case release
}

public struct Environment {
    public static var api: ApiFlavor = .alpha
    public static var build: BuildVariant = .debug
    public static var locale: Locale = Locale.current
}
