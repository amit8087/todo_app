package com.osi.todo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.osi.todo.AppDatabase
import com.osi.todo.databinding.ActivityAddTaskBinding
import com.osi.todo.entity.TodoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var appDb: AppDatabase
    private var documentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDb = AppDatabase.getDatabase(this)

        binding.btnSave.setOnClickListener {
            writeData()
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        }
        readData()
    }

    private fun writeData() {
        val todoTitle = binding.etTitle.text.toString()
        val todoDescription = binding.etDescription.text.toString()

        if (todoTitle.isNotEmpty() && todoDescription.isNotEmpty()) {
            val saveTodo = TodoInfo(
                if(documentId != -1) documentId else null, todoTitle, todoDescription
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDb.TodoInfoDao().insert(saveTodo)
            }
        }
    }

    private fun readData() {
        documentId = intent.getIntExtra("id", -1)
        println("Record Id : $documentId")

        if (documentId > 0) {
            GlobalScope.launch(Dispatchers.IO) {
                appDb.TodoInfoDao().getRecordById(id = documentId)?.let {
                    println("Message : ${it.title}")
                    binding.etTitle.setText(it.title)
                    binding.etDescription.setText(it.description)
                }
            }
        }
    }
}