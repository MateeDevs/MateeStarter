package kmp.shared.core

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseFlow
import kmp.shared.base.usecase.UseCaseFlowNoParams
import kmp.shared.base.usecase.UseCaseFlowResult
import kmp.shared.base.usecase.UseCaseFlowResultNoParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val iosDefaultScope: CoroutineScope = object : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Default
}

sealed class FlowWrapperParent<Params, T>(private val suspender: suspend (Params?) -> Flow<T>) {

    fun subscribe(
        params: Params? = null,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit,
    ): Job = iosDefaultScope.launch {
        suspender(params)
            .onEach {
                onEach(it)
            }
            .catch {
                onThrow(it)
            } // catch{} before onCompletion{} or else completion hits rx first and ends stream
            .onCompletion {
                onComplete()
            }
            .collect()
    }
}

class FlowWrapper<Params : Any, Out : Any>(flow: suspend (Params?) -> Flow<Out>) :
    FlowWrapperParent<Params, Out>(flow)

fun <Params : Any, Out : Any> UseCaseFlowResult<Params, Out>.subscribe(
    params: Params,
    onEach: (item: Result<Out>) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job =
    FlowWrapper<Params, Result<Out>> { invoke(params) }.subscribe(
        params,
        onEach,
        onComplete,
        onThrow,
    )

fun <Params : Any, Out : Any> UseCaseFlowResultNoParams<Out>.subscribe(
    onEach: (item: Result<Out>) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job =
    FlowWrapper<Params, Result<Out>> { invoke() }.subscribe(
        onEach = onEach,
        onComplete = onComplete,
        onThrow = onThrow,
    )

fun <Params : Any, Out : Any> UseCaseFlow<Params, Out>.subscribe(
    params: Params,
    onEach: (item: Out) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job =
    FlowWrapper<Params, Out> { invoke(params) }.subscribe(
        params,
        onEach,
        onComplete,
        onThrow,
    )

fun <Params : Any, Out : Any> UseCaseFlowNoParams<Out>.subscribe(
    onEach: (item: Out) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit,
): Job =
    FlowWrapper<Params, Out> { invoke() }.subscribe(
        onEach = onEach,
        onComplete = onComplete,
        onThrow = onThrow,
    )
