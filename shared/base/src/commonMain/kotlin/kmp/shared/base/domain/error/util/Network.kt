package kmp.shared.base.domain.error.util

import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kmp.shared.base.domain.error.domain.BackendError
import kmp.shared.base.domain.error.domain.CommonError
import kmp.shared.base.domain.model.Result

inline fun <R : Any> runCatchingCommonNetworkExceptions(block: () -> R): Result<R> =
    try {
        Result.Success(block())
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> Result.Error(
                BackendError.NotAuthorized(e.response.toString(), e),
            )

            else -> throw e
        }
    } catch (e: Throwable) {
        when (e::class.simpleName) { // Handle platform specific exceptions
            "UnknownHostException" -> Result.Error(CommonError.NoNetworkConnection(e))
            else -> throw e // Rethrow exception when it's not matched
        }
    }
