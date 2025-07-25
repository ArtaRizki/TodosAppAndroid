package com.example.todosapp.data.repository

import com.example.todosapp.data.api.ApiClient
import com.example.todosapp.data.model.Todo

class TodoRepository {
    private val apiService = ApiClient.apiService

    suspend fun getTodos(): List<Todo> {
        val response = apiService.getTodos()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch todos: ${response.message()}")
        }
    }

    suspend fun getTodo(id: Int): Todo {
        val response = apiService.getTodo(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Todo not found")
        } else {
            throw Exception("Failed to fetch todo: ${response.message()}")
        }
    }
}