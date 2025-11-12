// swiftlint:disable file_length
// swiftlint:disable force_cast

import Foundation
import KMPShared

private class JobWrapper {
    var job: Kotlinx_coroutines_coreJob?

    func setJob(_ job: Kotlinx_coroutines_coreJob?) {
        self.job = job
    }
}

/// `SkieSwiftFlow` does not propagate exceptions to Swift, we need custom handling
public extension UseCaseFlow {
    func execute<In: Any, Out>(params: In) -> AsyncThrowingStream<Out, Swift.Error> {
        let jobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Swift.Error> { continuation in
            let coroutineJob = subscribe(params) { data in
                let value: Out = data as! Out
                continuation.yield(value)
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
        }
    }
}

/// `SkieSwiftFlow` does not propagate exceptions to Swift, we need custom handling
public extension UseCaseFlowNoParams {
    func execute<Out>() -> AsyncThrowingStream<Out, Swift.Error> {
        let jobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Swift.Error> { continuation in
            let coroutineJob = subscribe { data in
                let value: Out = data as! Out
                continuation.yield(value)
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
        }
    }
}

/// `UseCaseFlowResult` does not automatically convert to `SkieSwiftFlow`, we need manual conversion
public extension UseCaseFlowResult {
    func execute<In: Any, Out>(params: In) -> AsyncThrowingStream<Out, Swift.Error> {
        let jobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Swift.Error> { continuation in
            let coroutineJob = subscribe(params) { result in
                switch onEnum(of: result) {
                case let .success(success): continuation.yield(success.data as! Out)
                case let .error(error): continuation.finish(throwing: error.error)
                }
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
        }
    }
}

/// `UseCaseFlowResultNoParams` does not automatically convert to `SkieSwiftFlow`, we need manual conversion
public extension UseCaseFlowResultNoParams {
    func execute<Out>() -> AsyncThrowingStream<Out, Swift.Error> {
        let jobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Swift.Error> { continuation in
            let coroutineJob = subscribe { result in
                switch onEnum(of: result) {
                case let .success(success): continuation.yield(success.data as! Out)
                case let .error(error): continuation.finish(throwing: error.error)
                }
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
        }
    }
}

public extension UseCaseResult {
    func execute<In: Any, Out>(params: In) async throws -> Out {
        let result = try await invoke(params: params)
        switch onEnum(of: result) {
        case let .success(success): return success.data as! Out
        case let .error(error): throw error.error
        }
    }

    // Void returning UC
    func execute<In: Any>(params: In) async throws {
        let result = try await invoke(params: params)
        switch onEnum(of: result) {
        case let .success(success): return
        case let .error(error): throw error.error
        }
    }
}

public extension UseCaseResultNoParams {
    func execute<Out>() async throws -> Out {
        let result = try await invoke()
        switch onEnum(of: result) {
        case let .success(success): return success.data as! Out
        case let .error(error): throw error.error
        }
    }

    // Void returning UC
    func execute() async throws {
        let result = try await invoke()
        switch onEnum(of: result) {
        case let .success(success): return
        case let .error(error): throw error.error
        }
    }
}

public extension UseCaseSynchronousResult {
    func execute<In: Any, Out>(params: In) throws -> Out {
        let result = try invoke(params: params)
        switch onEnum(of: result) {
        case let .success(success): return success.data as! Out
        case let .error(error): throw error.error
        }
    }

    // Void returning UC
    func execute<In: Any>(params: In) throws {
        let result = try invoke(params: params)
        switch onEnum(of: result) {
        case let .success(success): return
        case let .error(error): throw error.error
        }
    }
}

public extension UseCaseSynchronousResultNoParams {
    func execute<Out>() throws -> Out {
        let result = try invoke()
        switch onEnum(of: result) {
        case let .success(success): return success.data as! Out
        case let .error(error): throw error.error
        }
    }

    // Void returning UC
    func execute() throws {
        let result = try invoke()
        switch onEnum(of: result) {
        case let .success(success): return
        case let .error(error): throw error.error
        }
    }
}
