// swiftlint:disable file_length

import Foundation

private class JobWrapper {
    var job: Kotlinx_coroutines_coreJob?

    func setJob(_ job: Kotlinx_coroutines_coreJob?) {
        self.job = job
    }
}

/// `SkieSwiftFlow` does not propagate exceptions to Swift, we need custom handling
public extension UseCaseFlowNoParams {
    func execute<Out>() -> AsyncThrowingStream<Out, Error> {
        let jobWrapper: JobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Error> { continuation in
            let coroutineJob = subscribe { data in
                let value: Out = data as! Out // swiftlint:disable:this force_cast
                continuation.yield(value)
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
            continuation.onTermination = { _ in
                coroutineJob.cancel(cause: nil)
            }
        }
    }
}

// returns unwrapped result, for try await should be wrapped in do-catch block
/// `SkieSwiftFlow` does not propagate exceptions to Swift, we need custom handling
public extension UseCaseFlowResult {
    func execute<In: Any, Out>(params: In) -> AsyncThrowingStream<Out, Error> {
        let jobWrapper: JobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Error> { continuation in
            let coroutineJob = subscribe(params: params) { result in
                switch onEnum(of: result) {
                case .success(let resultSuccess):
                    // if new possible type is needed, it can be added to this switch
                    // swiftlint:disable force_cast
                    switch resultSuccess.data {
                    case let array as NSArray:
                        let arrayValue = (array as? [Any]) as! Out
                        continuation.yield(arrayValue)
                    case let boolean as KotlinBoolean:
                        let boolValue = boolean.boolValue as! Out
                        continuation.yield(boolValue)
                    default:
                        continuation.yield(resultSuccess as! Out)
                    }
                case .error(let resultError):
                    let resultError = resultError.error
                    continuation.finish(
                        throwing: KmmLocalizedError(
                            errorResult: resultError,
                            localizedMessage: resultError.localizedMessage.localized()
                        )
                    )
                }
            } onComplete: {
                continuation.finish()
            } onThrow: { error in
                continuation.finish(throwing: error.asError())
            }
            jobWrapper.setJob(coroutineJob)
            continuation.onTermination = { _ in
                coroutineJob.cancel(cause: nil)
            }
        }
    }
}

public extension UseCaseResult {
    func execute<In: Any, Out>(params: In) async throws -> Out {
        let res = try await invoke(params: params)

        switch onEnum(of: res) {
        case .error(let resultError):
            throw KmmLocalizedError(errorResult: resultError.error, localizedMessage: resultError.error.localizedMessage.localized())
        case .success(let resultSuccess):
            return resultSuccess.data as! Out
        }
    }

    func execute<In: Any>(params: In) async throws {
        let res = try await invoke(params: params)

        switch onEnum(of: res) {
        case .error(let resultError):
            throw KmmLocalizedError(errorResult: resultError.error, localizedMessage: resultError.error.localizedMessage.localized())
        case .success(let resultSuccess):
            return resultSuccess.data as! Void
        }
    }
}

public extension UseCaseResultNoParams {
    func execute<Out>() async throws -> Out {
        let res = try await invoke()

        switch onEnum(of: res) {
        case .error(let resultError):
            throw KmmLocalizedError(errorResult: resultError.error, localizedMessage: resultError.error.localizedMessage.localized())
        case .success(let resultSuccess):
            return resultSuccess.data as! Out
        }
    }


    // Void returining UC
    func execute() async throws {
        let res = try await invoke()

        switch onEnum(of: res) {
        case .error(let resultError):
            throw KmmLocalizedError(errorResult: resultError.error, localizedMessage: resultError.error.localizedMessage.localized())
        case .success(let resultSuccess):
            return resultSuccess.data as! Void
        }
    }
}
