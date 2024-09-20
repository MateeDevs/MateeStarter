package kmp.shared.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalObjCName

/**
 * This object is needed because of Tests on iOS platform.
 */
object FlowTestHelper {
    @OptIn(ExperimentalObjCName::class)
    fun <T : Any> arrayToFlow(@ObjCName(swiftName = "_") array: List<T>): Flow<T> = flow<T> {
        array.forEach {
            emit(it)
        }
    }
}
