package com.osi.todo

import com.osi.todo.entity.TodoInfo

interface DeleteListener {
    fun deleteRecordById(position: Int, item: TodoInfo)
}
