package com.halill.halill.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : MviState, E : MviEvent>(initial: S) {

    private val _state = MutableStateFlow(initial)
    val state: StateFlow<S> get() = _state

    fun sendEvent(event: E) {
        reduce(state.value, event)
    }

    abstract fun reduce(oldState: S, event: E)
}