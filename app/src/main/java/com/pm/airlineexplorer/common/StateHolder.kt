package com.pm.airlineexplorer.common

import kotlinx.coroutines.flow.Flow

abstract class StateHolder<P, T> {

    protected abstract val params : P

    abstract val state: Flow<T>

    abstract val initialState: T
}