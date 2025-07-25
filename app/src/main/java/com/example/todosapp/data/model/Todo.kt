package com.example.todosapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
) : Parcelable