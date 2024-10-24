//
//  Created by Petr Chmelar on 07.10.2023
//  Copyright © 2023 Matee. All rights reserved.
//

#if DEBUG
import CoreLocation
import DependencyInjection
import Factory
import KMPShared
@testable import SharedDomain
import SharedDomainMocks

public extension Container {
    func registerUseCaseMocks() {
        
        // Sample
        getSampleTextUseCase.register { GetSampleTextUseCaseMock(executeReturnValue: ResultSuccess(data: SampleText.stub)) }
    }
}
#endif
