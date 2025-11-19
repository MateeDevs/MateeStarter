//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

#if DEBUG
import CoreLocation
import DependencyInjection
import Factory
import KMPShared
@testable import SharedDomain
import SharedDomainMocks
import Utilities

public extension Container {
    func registerViewModelMocks() {
        
        // Sample
        sampleSharedViewModel.register {
            SharedViewModel(
                SampleSharedViewModel(getSampleText: self.getSampleTextUseCase(), trackAnalyticsEventUseCase: self.trackAnalyticsEventUseCase())
            )
        }
    }
}
#endif
