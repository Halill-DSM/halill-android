package com.halill.halill.features.todo.write

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.usecase.EditTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoDetailUseCase
import com.halill.domain.features.todo.usecase.SaveTodoUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WriteTodoViewModel @Inject constructor(
    private val saveTodoUseCase: SaveTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val getTodoDetailUseCase: GetTodoDetailUseCase
) : BaseViewModel<WriteTodoState, WriteTodoEvent>() {

    override val initialState: WriteTodoState
        get() = WriteTodoState.initial()

    fun setTitle(title: String) {
        sendEvent(WriteTodoEvent.InputTitle(title))
    }

    fun setContent(content: String) {
        sendEvent(WriteTodoEvent.InputContent(content))
    }

    fun writeTodo() {
        viewModelScope.launch {
            val parameter =
                WriteTodoParam(state.value.title, state.value.content, state.value.deadline, false)
            saveTodoUseCase.execute(parameter)
        }

    }

    fun showSelectDateState() {
        sendEvent(WriteTodoEvent.ShowSelectDateDialog)
    }

    fun showSelectTimeState() {
        sendEvent(WriteTodoEvent.ShowSelectTimeDialog)
    }

    fun dismissSelectDateState() {
        sendEvent(WriteTodoEvent.DismissSelectDateDialog)
    }

    fun dismissSelectTimeState() {
        sendEvent(WriteTodoEvent.DismissSelectTimeDialog)
    }

    fun setDeadlineYear(year: Int) {
        sendEvent(WriteTodoEvent.InputDeadlineYear(year))
    }

    fun setDeadlineMonth(month: Int) {
        sendEvent(WriteTodoEvent.InputDeadlineMonth(month))
    }

    fun setDeadlineDay(day: Int) {
        sendEvent(WriteTodoEvent.InputDeadlineDay(day))
    }

    fun setDeadlineHour(hour: Int) {
        sendEvent(WriteTodoEvent.InputDeadlineHour(hour))
    }

    fun setDeadlineMinute(minute: Int) {
        sendEvent(WriteTodoEvent.InputDeadlineMinute(minute))
    }

    private fun setDeadline(deadline: LocalDateTime) {
        sendEvent(WriteTodoEvent.InputDeadline(deadline))
    }

    private fun setIsForEdit(editTodoId: Long, isComplete: Boolean) {
        sendEvent(WriteTodoEvent.IsForEdit(editTodoId, isComplete))
    }

    suspend fun getTodoDataWhenEdit(id: Long) {
        val todoData = getTodoDetailUseCase.execute(id)
        setIsForEdit(id, todoData.isCompleted)
        setTitle(todoData.title)
        setContent(todoData.content)
        setDeadline(todoData.deadline)
    }

    fun editTodo() {
        val todoData = WriteTodoParam(
            title = state.value.title,
            content = state.value.content,
            deadline = state.value.deadline,
            isCompleted = state.value.editTodoIsComplete
        )
        val param = EditTodoParam(
            todoId = state.value.editTodoId,
            data = todoData
        )
        viewModelScope.launch {
            editTodoUseCase.execute(param)
        }
    }

    override fun reduceEvent(oldState: WriteTodoState, event: WriteTodoEvent) {
        when (event) {
            is WriteTodoEvent.InputTitle -> {
                setState(oldState.copy(title = event.title))
            }
            is WriteTodoEvent.InputContent -> {
                setState(oldState.copy(content = event.content))
            }
            is WriteTodoEvent.InputDeadlineYear -> {
                setState(oldState.copy(deadline = oldState.deadline.changeYear(event.year)))
            }
            is WriteTodoEvent.InputDeadlineMonth -> {
                setState(oldState.copy(deadline = oldState.deadline.changeMonth(event.month)))
            }
            is WriteTodoEvent.InputDeadlineDay -> {
                setState(oldState.copy(deadline = oldState.deadline.changeDay(event.day)))
            }
            is WriteTodoEvent.InputDeadlineHour -> {
                setState(oldState.copy(deadline = oldState.deadline.changeHour(event.hour)))
            }
            is WriteTodoEvent.InputDeadlineMinute -> {
                setState(oldState.copy(deadline = oldState.deadline.changeMinute(event.minute)))
            }
            is WriteTodoEvent.IsForEdit -> {
                setState(oldState.copy(isEdit = true, editTodoId = event.editTodoId))
            }
            is WriteTodoEvent.ShowSelectDateDialog -> {
                setState(oldState.copy(showDateSelectDialog = true))
            }
            is WriteTodoEvent.ShowSelectTimeDialog -> {
                setState(oldState.copy(showHourSelectDialog = true))
            }
            is WriteTodoEvent.DismissSelectDateDialog -> {
                setState(oldState.copy(showDateSelectDialog = false))
            }
            is WriteTodoEvent.DismissSelectTimeDialog -> {
                setState(oldState.copy(showHourSelectDialog = false))
            }
            is WriteTodoEvent.InputDeadline -> {
                setState(oldState.copy(deadline = event.deadline))
            }
        }
    }
}