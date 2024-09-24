package kmp.shared.base.error.domain

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kmp.shared.base.ErrorResult
import kmp.shared.base.MR

/**
 * Error type used anywhere in the project. Contains subclasses for common exceptions that can happen anywhere
 * @param throwable optional [Throwable] parameter used for debugging or crash reporting
 */
sealed class CommonError(localizedMessage: StringDesc, throwable: Throwable? = null) : ErrorResult(
    localizedMessage = localizedMessage,
    throwable = throwable,
) {
    class NoNetworkConnection(throwable: Throwable?) : CommonError(localizedMessage = MR.strings.error_no_internet_connection.desc(), throwable = throwable)
    data object Unknown : CommonError(localizedMessage = MR.strings.unknown_error.desc())
}
