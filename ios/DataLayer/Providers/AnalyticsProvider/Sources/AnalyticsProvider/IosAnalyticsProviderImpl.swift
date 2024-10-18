//
//  Created by Petr Chmelar on 26.09.2021
//  Copyright © 2021 Matee. All rights reserved.
//

import KMPShared
import OSLog
import Utilities

/**
 * iOS implementation of the AnalyticsSource from shared module
 */
public class IosAnalyticsProviderImpl: AnalyticsProvider {
    
    public init() { }
    
    public func logEvent(event: AnalyticsEvent) {
        // TODO: Implement analytics event logging
        Logger.app.error("Analytics event logging not implemented")
    }
}
