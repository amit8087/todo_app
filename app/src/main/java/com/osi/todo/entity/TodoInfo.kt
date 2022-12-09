package com.osi.todo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_info")
data class TodoInfo (
    @PrimaryKey(autoGenerate = true) val Id: Int?,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "description") val description : String
)