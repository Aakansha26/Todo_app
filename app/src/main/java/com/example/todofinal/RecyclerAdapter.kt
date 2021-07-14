package com.example.todofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var todolist: ArrayList<Todo>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todolist[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int {
        return todolist.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(todo: Todo) {
            itemView.findViewById<CheckBox>(R.id.todotitle_cb).text = todo.todotitle
            itemView.findViewById<TextView>(R.id.todomsg_text).text = todo.todomsg
        }
    }
}