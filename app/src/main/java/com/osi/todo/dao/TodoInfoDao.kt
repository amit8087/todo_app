package com.osi.todo.dao

import androidx.room.*
import com.osi.todo.entity.TodoInfo

@Dao
interface TodoInfoDao {
    @Query("SELECT * FROM todo_info")
    fun getAll(): MutableList<TodoInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: TodoInfo)

    @Delete
    suspend fun delete(user: TodoInfo)

    @Query("DELETE FROM todo_info")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_info WHERE ID = :id")
    suspend fun getRecordById(id: Int): TodoInfo?

}