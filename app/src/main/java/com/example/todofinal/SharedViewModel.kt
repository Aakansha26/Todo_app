package com.example.todofinal

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val allTodos: LiveData<List<Todo>>
    val currentTodo = MutableLiveData<Todo?>()
    private val repository: Repository

    init {

        val dao = TodoDatabase.getDatabase(application).getTodoDao()
        repository = Repository(dao)
        allTodos = repository.alltodos
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
         repository.delete(todo)
    }

    fun insertTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

    fun changeCurrentTodo(todo: Todo) {
        currentTodo.value = todo
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(todo)
        currentTodo.value = null
    }
}