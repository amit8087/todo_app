package com.osi.todo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.osi.todo.entity.TodoInfo
import com.osi.todo.ui.AddTaskActivity


class TodoAdapter(
    private val context: Context,
    private var toDo: MutableList<TodoInfo>,
    val listener: DeleteListener
) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var todoTitle = itemView.findViewById<TextView>(R.id.tv_title)
        var todoDescription = itemView.findViewById<TextView>(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val toDoItem = toDo[position]
        holder.todoTitle.text = toDoItem.title
        holder.todoDescription.text = toDoItem.description

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddTaskActivity::class.java)
            // passing id with intent
            intent.putExtra("id", toDoItem.Id)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Do you want to delete the item?")
            builder.setTitle("Alert !")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes") { dialog, which ->
                listener.deleteRecordById(position = position, item = toDoItem)
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return toDo.size
    }

    fun updateList(itemsList: MutableList<TodoInfo>) {
        toDo = itemsList
//        notifyDataSetChanged()
    }

    fun removeItemUpdateUI(position: Int) {
        toDo.removeAt(position)
        notifyItemRemoved(position)
    }
}