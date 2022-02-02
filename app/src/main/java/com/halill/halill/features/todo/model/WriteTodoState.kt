package com.halill.halill.features.todo.model

sealed class WriteTodoState {
    object DoneInputState : WriteTodoState()
    object NotDoneInputState : WriteTodoState()
    object SelectDateState : WriteTodoState()
    object SelectTimeState : WriteTodoState()
}