// swiftlint:disable file_length

import Foundation
import KMPShared

private class JobWrapper {
    var job: Kotlinx_coroutines_coreJob?

    func setJob(_ job: Kotlinx_coroutines_coreJob?) {
        self.job = job
    }
}

public extension UseCaseFlowNoParams {
    func execute<Out>() -> AsyncStream<Out> {
        let _: JobWrapper = JobWrapper()
        return AsyncStream<Out> { continuation in
            let coroutineJob = subscribe { data in
                let value: Out = data as! Out // swiftlint:disable:this force_cast
                continuation.yield(value)
            } onComplete: {
                continuation.finish()
            } onThrow: { _ in
                continuation.finish()
            }
            continuation.onTermination = { _ in
                coroutineJob.cancel(cause: nil)
            }
        }
    }
}

// returns unwrapped result, for try await should be wrapped in do-catch block
public extension UseCaseFlowResult {
    func execute<In: Any, Out>(params: In) -> AsyncThrowingStream<Out, Error> {
        let _: JobWrapper = JobWrapper()
        return AsyncThrowingStream<Out, Error> { continuation in
            let coroutineJob = subscribe(params: params) { result in
                switch result {
                case let resultSuccess as ResultSuccess<AnyObject>:
                    // if new possible type is needed, it can be added to this switch
                    // swiftlint:disable force_cast
                    switch resultSuccess.data {
                    case let resultSuccess as NSArray:
                        let arrayValue = (resultSuccess as? [Any]) as! Out
                        continuation.yield(arrayValue)
                    case let resultSuccess as KotlinBoolean:
                        let boolValue = resultSuccess.boolValue as! Out
                        continuation.yield(boolValue)
                    default:
                        continuation.yield(resultSuccess as! Out)
                    }
                    // swiftlint:enable force_cast
                case let resultError as ResultError<AnyObject>:
                    let resultError = resultError.error
                    continuation.finish(
                        throwing: KmmLocalizedError(
                            errorResult: resultError,
                            localizedMessage: resultError.localizedMessage.localized()
                        )
                    )
                default:
                    let resultError = CommonError.Unknown()
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
            continuation.onTermination = { _ in
                coroutineJob.cancel(cause: nil)
            }
        }
    }
}

public extension UseCaseResult {
    func execute<In: Any, Out>(params: In) async throws -> Out {
        let jobWrapper: JobWrapper = JobWrapper()
        return try await withTaskCancellationHandler(
            operation: {
                try await withCheckedThrowingContinuation { continuation in

                    let coroutineJob = subscribe(
                        params: params,
                        onSuccess: { result in
                            // swiftlint:disable force_cast
                            guard result is ResultSuccess else {
                                let errorResult = (result as! ResultError).error
                                continuation.resume(throwing: KmmLocalizedError(
                                    errorResult: errorResult,
                                    localizedMessage: errorResult.localizedMessage.localized()
                                ))
                                return
                            }
                            let value: Out = (result as! ResultSuccess).data as! Out
                            continuation.resume(returning: value)
                            return
                            // swiftlint:enable force_cast
                        },
                        onThrow: { kotlinThrowable in
                            continuation.resume(throwing: KmmLocalizedError(
                                errorResult: nil,
                                localizedMessage: kotlinThrowable.message ?? kotlinThrowable.description()
                            ))
                        })
                    jobWrapper.setJob(coroutineJob)
                }
            },
            onCancel: {[jobWrapper] in
                jobWrapper.job?.cancel(cause: nil)
            }
        )
    }

    func execute<In: Any>(params: In) async throws {
        let jobWrapper: JobWrapper = JobWrapper()
        return try await withTaskCancellationHandler(
            operation: {
                try await withCheckedThrowingContinuation { continuation in

                    let coroutineJob = subscribe(
                        params: params,
                        onSuccess: { result in
                            guard result is ResultSuccess else {
                                let errorResult = (result as! ResultError).error // swiftlint:disable:this force_cast
                                continuation.resume(throwing: KmmLocalizedError(
                                    errorResult: errorResult,
                                    localizedMessage: errorResult.localizedMessage.localized()
                                ))
                                return
                            }
                            continuation.resume()
                            return
                        },
                        onThrow: { kotlinThrowable in
                            continuation.resume(throwing: KmmLocalizedError(
                                errorResult: nil,
                                localizedMessage: kotlinThrowable.message ?? kotlinThrowable.description()
                            ))
                        })
                    jobWrapper.setJob(coroutineJob)
                }
            },
            onCancel: {[jobWrapper] in
                jobWrapper.job?.cancel(cause: nil)
            }
        )
    }
}

public extension UseCaseResultNoParams {
    func execute<Out>() async throws -> Out {
        let jobWrapper: JobWrapper = JobWrapper()
        return try await withTaskCancellationHandler(
            operation: {
                try await withCheckedThrowingContinuation { continuation in

                    let coroutineJob = subscribe(
                        onSuccess: { result in
                            // swiftlint:disable force_cast
                            guard result is ResultSuccess else {
                                let errorResult = (result as! ResultError).error
                                continuation.resume(throwing: KmmLocalizedError(
                                    errorResult: errorResult,
                                    localizedMessage: errorResult.localizedMessage.localized()
                                ))
                                return
                            }
                            let value: Out = (result as! ResultSuccess).data as! Out
                            continuation.resume(returning: value)
                            return
                            // swiftlint:enable force_cast
                        },
                        onThrow: { kotlinThrowable in
                            continuation.resume(throwing: KmmLocalizedError(
                                errorResult: nil,
                                localizedMessage: kotlinThrowable.message ?? kotlinThrowable.description()
                            ))
                        })
                    jobWrapper.setJob(coroutineJob)
                }
            },
            onCancel: {[jobWrapper] in
                jobWrapper.job?.cancel(cause: nil)
            }
        )
    }

    // Void returining UC
    func execute() async throws {
        let jobWrapper: JobWrapper = JobWrapper()
        return try await withTaskCancellationHandler(
            operation: {
                try await withCheckedThrowingContinuation { continuation in

                    let coroutineJob = subscribe(
                        onSuccess: { result in
                            guard result is ResultSuccess else {
                                let errorResult = (result as! ResultError).error // swiftlint:disable:this force_cast
                                continuation.resume(throwing: KmmLocalizedError(
                                    errorResult: errorResult,
                                    localizedMessage: errorResult.localizedMessage.localized()
                                ))
                                return
                            }
                            continuation.resume()
                            return
                        },
                        onThrow: { kotlinThrowable in
                            continuation.resume(throwing: KmmLocalizedError(
                                errorResult: nil,
                                localizedMessage: kotlinThrowable.message ?? kotlinThrowable.description()
                            ))
                        })
                    jobWrapper.setJob(coroutineJob)
                }
            },
            onCancel: {[jobWrapper] in
                jobWrapper.job?.cancel(cause: nil)
            }
        )
    }
}
