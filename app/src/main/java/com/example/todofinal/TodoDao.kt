package com.example.todofinal

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todofinal.screens.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("SELECT * FROM Todo_table ORDER BY id ASC")
    fun getAllTodos(): LiveData<List<Todo>>
}