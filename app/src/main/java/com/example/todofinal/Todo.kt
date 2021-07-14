package com.example.todofinal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_table")
class Todo (@ColumnInfo(name="todotitle") var todotitle: String, @ColumnInfo(name = "todomsg") var todomsg: String?) {
    @PrimaryKey(autoGenerate = true) var id = 0
}