//
//  Created by David Kadlček on 27.02.2023
//  Copyright © 2023 Matee. All rights reserved.
//

import Foundation

open class UseCaseFlowNoParamsMock<Out>: UseCaseFlowNoParams {
    
    public var executeCallsCount = 0
    public var executeCalled: Bool {
        return executeCallsCount > 0
    }
    public var executeReturnValue: Out?
    
    public init() {}
    
    public init(executeReturnValue: Out?) {
        self.executeReturnValue = executeReturnValue
    }
    
    // MARK: - execute
    public func __invoke() async throws -> SkieSwiftFlow<Any> {
        executeCallsCount += 1
        
        guard let executeReturnValue else {
            return FlowTestHelper.shared.arrayToFlow(array: [])
        }
        
        guard let executeReturnValue = executeReturnValue as? [Out] else {
            return FlowTestHelper.shared.arrayToFlow(array: [executeReturnValue] as! [Out]) // swiftlint:disable:this force_cast
        }
        
        return FlowTestHelper.shared.arrayToFlow(array: executeReturnValue)
    }
}
