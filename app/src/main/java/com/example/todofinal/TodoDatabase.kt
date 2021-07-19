package com.example.todofinal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Todo::class), version = 2, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        val migration_1_2: Migration = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Todo_table ADD COLUMN priority TEXT DEFAULT 'LOW' NOT NULL")
            }

        }

        fun getDatabase(context: Context): TodoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).addMigrations(migration_1_2)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

