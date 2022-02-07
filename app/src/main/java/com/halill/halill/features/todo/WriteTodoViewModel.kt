package com.halill.halill.features.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.usecase.EditTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoDetailUseCase
import com.halill.domain.features.todo.usecase.SaveTodoUseCase
import com.halill.halill.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WriteTodoViewModel @Inject constructor(
    private val saveTodoUseCase: SaveTodoUseCase,
    private val editTodoUseCase: EditTodoUseCase,
    private val getTodoDetailUseCase: GetTodoDetailUseCase
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _deadline = MutableStateFlow(LocalDateTime.now())
    val deadline: StateFlow<LocalDateTime> = _deadline

    private val _writeTodoState = MutableStateFlow<WriteTodoState>(WriteTodoState.NotDoneInputState)
    val writeTodoState: StateFlow<WriteTodoState> = _writeTodoState

    var editTodoId = -1L
    var editIsCompleted = false
    
    fun setTitle(title: String) {
        viewModelScope.launch {
            _title.value = title
            checkDoneInput()
        }
    }

    fun setContent(content: String) {
        viewModelScope.launch {
            _content.value = content
            checkDoneInput()
        }
    }

    fun writeTodo() {
        viewModelScope.launch {
            val parameter = WriteTodoParam(title.value, content.value, deadline.value, false)
            saveTodoUseCase.execute(parameter)
        }

    }

    fun checkDoneInput() {
        _writeTodoState.value =
            if (title.value.isBlank() || content.value.isBlank()) WriteTodoState.NotDoneInputState
            else WriteTodoState.DoneInputState
    }

    fun setSelectDateState() {
        _writeTodoState.value = WriteTodoState.SelectDateState
    }

    fun setSelectTimeState() {
        _writeTodoState.value = WriteTodoState.SelectTimeState
    }

    fun setDeadlineYear(year: Int) {
        val originalDeadline = deadline.value
        _deadline.value = originalDeadline.changeYear(year)
    }

    fun setDeadlineMonth(month: Int) {
        val originalDeadline = deadline.value
        _deadline.value = originalDeadline.changeMonth(month)
    }

    fun setDeadlineDay(day: Int) {
        val originalDeadline = deadline.value
        _deadline.value = originalDeadline.changeDay(day)
    }

    fun setDeadlineHour(hour: Int) {
        val originalDeadline = deadline.value
        _deadline.value = originalDeadline.changeHour(hour)
    }

    fun setDeadlineMinute(minute: Int) {
        val originalDeadline = deadline.value
        _deadline.value = originalDeadline.changeMinute(minute)
    }


    suspend fun getTodoDataWhenEdit(id: Long) {
        val todoData = getTodoDetailUseCase.execute(id)
        _writeTodoState.value = WriteTodoState.EditTodoState(todoData)
        editTodoId = todoData.id
        editIsCompleted = todoData.isCompleted
        _title.value = todoData.title
        _content.value = todoData.content
        _deadline.value = todoData.deadline
    }

    fun editTodo() {
        val todoData = WriteTodoParam(
            title = title.value,
            content = content.value,
            deadline = deadline.value,
            isCompleted = editIsCompleted
        )
        val param = EditTodoParam(
            todoId = editTodoId,
            data = todoData
        )
        viewModelScope.launch {
            editTodoUseCase.execute(param)
        }
    }
}