package com.halill.halill.features.todo.write

import com.halill.domain.features.todo.entity.TodoEntity

sealed class WriteTodoState {
    object DoneInputState : WriteTodoState()
    object NotDoneInputState : WriteTodoState()
    object SelectDateState : WriteTodoState()
    object SelectTimeState : WriteTodoState()
    data class EditTodoState(val todo: TodoEntity) : WriteTodoState()
}