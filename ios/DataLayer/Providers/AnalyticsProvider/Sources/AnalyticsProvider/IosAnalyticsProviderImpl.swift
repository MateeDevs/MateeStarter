//
//  Created by Petr Chmelar on 26.09.2021
//  Copyright © 2021 Matee. All rights reserved.
//

import Firebase
import FirebaseAnalytics
import KMPShared

/**
 * iOS implementation of the AnalyticsSource from shared module
 */
public class IosAnalyticsProviderImpl: AnalyticsProvider {
    
    public init() {
        // Start Firebase if not yet started
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }
    
    public func logEvent(event: AnalyticsEvent) {
        Analytics.logEvent(event.eventName, parameters: event.parameters)
    }
}
