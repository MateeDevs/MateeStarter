package kmp.shared.base.domain.model

import dev.icerock.moko.resources.desc.StringDesc

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error<out T : Any>(val error: ErrorResult, val data: T? = null) : Result<T>()
}

abstract class ErrorResult(val localizedMessage: StringDesc, open val throwable: Throwable? = null)
