package com.osi.todo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.osi.todo.TodoAdapter
import com.osi.todo.AppDatabase
import com.osi.todo.DeleteListener
import com.osi.todo.databinding.ActivityMainBinding
import com.osi.todo.entity.TodoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), DeleteListener {
    private lateinit var adapter: TodoAdapter
    private lateinit var appDb: AppDatabase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        appDb = AppDatabase.getDatabase(this)

        setUpViews()
    }

    private fun setUpViews() {
        adapter = TodoAdapter(this@MainActivity, mutableListOf(), listener = this)
        binding.toDoList.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.toDoList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getToDoList()
    }

    private fun getToDoList() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = appDb.TodoInfoDao().getAll()
            adapter.updateList(list)
        }

        adapter.notifyDataSetChanged()
    }

    override fun deleteRecordById(position: Int, item: TodoInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            // Need to call delete method from todo dao
            appDb.TodoInfoDao().delete(item)
        }
        // remove the item from UI in recycler view
        adapter.removeItemUpdateUI(position)
    }
}