package kmp.shared.base.usecase

import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kotlinx.coroutines.flow.Flow

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 *
 */
interface UseCaseResult<in Params, out T : Any> {
    @Throws(Throwable::class)
    suspend operator fun invoke(params: Params): Result<T>
}

interface UseCaseSynchronousResult<in Params, out T : Any> {
    @Throws(Throwable::class)
    operator fun invoke(params: Params): Result<T>
}

interface UseCaseSynchronousResultNoParams<out T : Any> {
    @Throws(Throwable::class)
    operator fun invoke(): Result<T>
}

interface UseCaseResultNoParams<out T : Any> {
    @Throws(Throwable::class)
    suspend operator fun invoke(): Result<T>
}

interface UseCaseFlowResult<in Params, out T : Any> {
    operator fun invoke(params: Params): Flow<Result<T>>
}

interface UseCaseFlowResultNoParams<out T : Any> {
    operator fun invoke(): Flow<Result<T>>
}

interface UseCaseSynchronousNoResult<in Params, out T : Any> {
    @Throws(Throwable::class)
    operator fun invoke(params: Params): T
}

/**
 * This fun applies fix if error is returned from getResult
 *
 * @param fix - fix to apply if error is returned from getResult. Should return true if error was fixed, false otherwise
 */
internal inline fun <T : Any> runHandlingError(
    fix: (ErrorResult) -> Boolean,
    getResult: () -> Result<T>,
): Result<T> =
    when (val result = getResult()) {
        is Result.Success -> result
        is Result.Error -> {
            val isFixed = fix(result.error)
            if (isFixed) {
                getResult()
            } else {
                result
            }
        }
    }