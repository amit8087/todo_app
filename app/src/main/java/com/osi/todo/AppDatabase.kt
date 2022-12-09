package com.osi.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osi.todo.dao.TodoInfoDao
import com.osi.todo.entity.TodoInfo

//The following code defines an AppDatabase class to hold the database.
@Database(entities = [TodoInfo::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun TodoInfoDao(): TodoInfoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}