package com.halill.halill.features.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.usecase.SaveTodoUseCase
import com.halill.halill.features.todo.model.WriteTodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WriteTodoViewModel @Inject constructor(
    private val saveTodoUseCase: SaveTodoUseCase
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _deadLine = MutableStateFlow(LocalDateTime.now())
    val deadLine: StateFlow<LocalDateTime> = _deadLine

    private val _writeTodoState = MutableStateFlow<WriteTodoState>(WriteTodoState.NotDoneInputState)
    val writeTodoState: StateFlow<WriteTodoState> = _writeTodoState

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

    fun setDeadLine(deadLine: LocalDateTime) {
        viewModelScope.launch {
            _deadLine.value = deadLine
        }
    }

    fun writeTodo() {
        viewModelScope.launch {
            val parameter = WriteTodoParam(title.value, content.value, deadLine.value, false)
            saveTodoUseCase.execute(parameter)
        }

    }

    private fun checkDoneInput() {
        _writeTodoState.value =
            if (title.value.isEmpty() || content.value.isEmpty()) WriteTodoState.NotDoneInputState
            else WriteTodoState.DoneInputState
    }

}