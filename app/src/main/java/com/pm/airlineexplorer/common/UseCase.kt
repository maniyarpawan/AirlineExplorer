package com.pm.airlineexplorer.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalTypeInference

abstract class UseCase<P, R> {
    operator fun invoke(params: P): Flow<R> = flow {
        emit(doWork(params))
    }

    protected abstract suspend fun doWork(params: P): R

}

@OptIn(ExperimentalTypeInference::class)
fun launchFlow(@BuilderInference block: suspend FlowCollector<Unit>.() -> Unit): Flow<Unit> =
    flow {
        emit(Unit)
        block()
    }