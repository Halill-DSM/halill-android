package com.halill.halill.main

import com.halill.halill.base.MviEvent

sealed class MainEvent : MviEvent {
    data class SetUser(val name: String, val email: String) : MainEvent()
    object NeedLogin : MainEvent()
    object DoneLogin : MainEvent()
}