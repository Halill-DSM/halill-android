package com.halill.halill.features.todo

sealed class WriteTodoState {
    object DoneInputState : WriteTodoState()
    object NotDoneInputState : WriteTodoState()
    object SelectDateState : WriteTodoState()
    object SelectTimeState : WriteTodoState()
}