package com.halill.halill.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : MviState> : ViewModel() {
    abstract val state: StateFlow<S>
}