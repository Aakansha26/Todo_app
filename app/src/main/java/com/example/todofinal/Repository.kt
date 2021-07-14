package com.example.todofinal

import androidx.lifecycle.LiveData

class Repository(private val todoDao: TodoDao) {

    val alltodos: LiveData<List<Todo>> = todoDao.getAllTodos()

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }
}