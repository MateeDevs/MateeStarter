//
//  Created by Petr Chmelar on 06.10.2023
//  Copyright © 2023 Matee. All rights reserved.
//

import Factory
import KMPShared
import SharedDomain

public extension Container {
    // Koin
    private var kmp: Factory<KMPDependency> { self { KMPKoinDependency() }.singleton }
    
    // Analytics
    var trackAnalyticsEventUseCase: Factory<TrackAnalyticsEventUseCase> { self { self.kmp().getProtocol(TrackAnalyticsEventUseCase.self) } }
    
    // Sample
    var getSampleTextUseCase: Factory<GetSampleTextUseCase> { self { self.kmp().getProtocol(GetSampleTextUseCase.self) } }
}
