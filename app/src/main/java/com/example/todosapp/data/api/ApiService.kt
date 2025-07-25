package com.example.todosapp.data.api

import com.example.todosapp.data.model.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Response<Todo>
}
