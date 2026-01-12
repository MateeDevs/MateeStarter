package kmp.shared.base.domain.util.extension

import kmp.shared.base.domain.model.ErrorResult
import kmp.shared.base.domain.model.Result
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** Transform Result data object to Result<Unit> */
fun <T : Any> Result<T>.toEmptyResult(): Result<Unit> = map { }

/**
 * Creates a [Result.Success] from [A], the receiver.
 */
fun <A : Any> A.success(): Result<A> = Result.Success(this)

/**
 * Creates a [Result.Error] from the receiver.
 */
fun <A : Any> ErrorResult.error(): Result<A> = Result.Error(this)

/** Fold Result data object */
@OptIn(ExperimentalContracts::class)
inline fun <T : Any, R> Result<T>.fold(
    success: (data: T) -> R,
    error: (error: ErrorResult) -> R,
): R {
    contract {
        callsInPlace(success, InvocationKind.AT_MOST_ONCE)
        callsInPlace(error, InvocationKind.AT_MOST_ONCE)
    }

    return when (this) {
        is Result.Success -> success(data)
        is Result.Error -> error(this.error)
    }
}

/** Transform Result data object */
inline fun <T : Any, R : Any> Result<T>.map(transform: (T) -> R) =
    flatMap { Result.Success(transform(it)) }

/** Transform Result data object */
inline fun <T : Any, R : Any> Result<T>.flatMap(transform: (T) -> Result<R>) =
    fold(success = { transform(it) }, error = { Result.Error(it) })

/**
 * Runs the [action] if the [Result] is s [Result.Success].
 * @return The original result
 */
inline fun <T : Any> Result<T>.alsoOnSuccess(action: (T) -> Unit): Result<T> =
    apply { if (this is Result.Success) action(data) }

/**
 * Runs the [action] if the [Result] is an [Result.Error].
 * @return The original result
 */
inline fun <T : Any> Result<T>.alsoOnError(action: (ErrorResult) -> Unit): Result<T> =
    apply { if (this is Result.Error) action(error) }

/**
 * Returns this [Result] if it's a [Result.Success] or returns [recover] when this [Result] is [Result.Error]
 */
inline fun <T : Any> Result<T>.getOrElse(recover: (ErrorResult) -> T): T =
    fold(success = { it }, error = { recover(it) })

/**
 * Returns the [data] if this [Result] is a [Result.Success] or null otherwise.
 */
fun <T : Any> Result<T>.getOrNull(): T? = fold(success = { it }, error = { null })

/**
 * Returns this [Result] if it's a [Result.Success] or returns the [Result] of [recover] when this [Result] is [Result.Error]
 */
inline fun <T : Any> Result<T>.recover(recover: (ErrorResult) -> Result<T>): Result<T> =
    fold(success = { Result.Success(it) }, error = { recover(it) })

/**
 * Returns the inner [Result] if the outer one is a [Result.Success] or the outer one if it is an [Result.Error]
 */
fun <T : Any> Result<Result<T>>.flatten(): Result<T> =
    fold(success = { it }, error = { Result.Error(it) })
