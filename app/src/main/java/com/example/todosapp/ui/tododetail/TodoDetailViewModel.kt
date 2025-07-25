package com.example.todosapp.ui.tododetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todosapp.data.model.Todo
import com.example.todosapp.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoDetailViewModel : ViewModel() {
    private val repository = TodoRepository()

    private val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo> = _todo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadTodo(todoId: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val todo = repository.getTodo(todoId)
                _todo.value = todo
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun setTodo(todo: Todo) {
        _todo.value = todo
    }
}