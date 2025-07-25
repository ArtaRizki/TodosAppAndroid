package com.example.todosapp.ui.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todosapp.data.model.Todo
import com.example.todosapp.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoListViewModel : ViewModel() {
    private val repository = TodoRepository()

    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> = _todos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> = _error

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val todoList = repository.getTodos()
                _todos.value = todoList
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun retry() {
        loadTodos()
    }
}