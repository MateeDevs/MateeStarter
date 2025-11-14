package kmp.shared.base.domain.error.domain

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kmp.shared.base.MR
import kmp.shared.base.domain.model.ErrorResult

/**
 * Error type used when handling responses from backend
 * @param throwable optional [Throwable] parameter used for debugging or crash reporting
 */
sealed class BackendError(
    localizedMessage: StringDesc,
    throwable: Throwable? = null,
    val responseMessage: String? = null,
) : ErrorResult(
    localizedMessage = localizedMessage,
    throwable = throwable,
) {

    class NotAuthorized(
        responseMessage: String? = null,
        throwable: Throwable? = null,
    ) : BackendError(
        localizedMessage = MR.strings.not_authorized.desc(),
        responseMessage = responseMessage,
        throwable = throwable,
    )
}
