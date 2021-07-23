package com.example.todofinal.screens

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_table")
class Todo (@ColumnInfo(name="todotitle") var todotitle: String, @ColumnInfo(name = "todomsg") var todomsg: String?, @ColumnInfo(name= "priority") var priority: String, @ColumnInfo(name= "isCompleted") var isCompleted: Boolean) {
    @PrimaryKey(autoGenerate = true) var id = 0
}

//