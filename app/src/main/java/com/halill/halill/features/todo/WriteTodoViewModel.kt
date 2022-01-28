package com.halill.halill.features.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.usecase.SaveTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WriteTodoViewModel @Inject constructor(
    private val saveTodoUseCase: SaveTodoUseCase
): ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _deadLine = MutableStateFlow(LocalDateTime.now())
    val deadLine: StateFlow<LocalDateTime> = _deadLine

    fun setTitle(title: String) {
        viewModelScope.launch {
            _title.value = title
        }
    }

    fun setContent(content: String) {
        viewModelScope.launch {
            _content.value = content
        }
    }

    fun setDeadLine(deadLine: LocalDateTime) {
        viewModelScope.launch {
            _deadLine.value = deadLine
        }
    }


}