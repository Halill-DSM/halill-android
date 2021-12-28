package com.halill.data.features.todo.remote

import com.halill.data.features.todo.dto.response.GetTodoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {
    @GET("/todo")
    suspend fun getTodoList(@Query("email") email: String): List<GetTodoResponse>
}