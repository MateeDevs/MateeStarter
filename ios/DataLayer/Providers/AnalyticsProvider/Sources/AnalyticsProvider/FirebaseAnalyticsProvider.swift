//
//  Created by Petr Chmelar on 26.09.2021
//  Copyright © 2021 Matee. All rights reserved.
//

import Firebase
import FirebaseAnalytics
import KMPShared

public class FirebaseAnalyticsProvider {
    public init() {
        // Start Firebase if not yet started
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }
    }
}

extension FirebaseAnalyticsProvider: AnalyticsProvider {
    
    public func logEvent(event: AnalyticsEvent) {
        
        let params = Dictionary(uniqueKeysWithValues: event.parameters.map { key, value in
            (key.parameterName, value)
        })
        
        Analytics.logEvent(event.eventName.eventName, parameters: params)
    }
}
