package com.example.todofinal

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todofinal.screens.Todo

class RecyclerAdapter(val listenermain: IListenerMain):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var alltodos =  ArrayList<Todo>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val todotitle: CheckBox = itemView.findViewById<CheckBox>(R.id.todotitle_cb)
        val todomsg = itemView.findViewById<TextView>(R.id.todomsg_text)
        val priority = itemView.findViewById<TextView>(R.id.priority_tv)
        val delete_button = itemView.findViewById<ImageView>(R.id.delete_button)
        val edit_button = itemView.findViewById<ImageView>(R.id.edit_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ViewHolder(layoutInflater.inflate(R.layout.todo, parent, false))
        viewHolder.delete_button.setOnClickListener{
            listenermain.onDeleteClicked(alltodos[viewHolder.adapterPosition])
        }

        viewHolder.edit_button.setOnClickListener{ view: View ->
            listenermain.onEditClicked(view, alltodos[viewHolder.adapterPosition])

        }

        viewHolder.todotitle.setOnClickListener() {
            viewHolder.todotitle.setChecked(true)
            Handler(Looper.getMainLooper()).postDelayed({
                listenermain.onTodoCompleted(alltodos[viewHolder.adapterPosition])
            }, 1200)


        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = alltodos[position]
        holder.todotitle.text = todo.todotitle
        holder.todomsg.text = todo.todomsg
        holder.priority.text = todo.priority

        if(todo.isCompleted)
        {
            holder.todotitle.setChecked(true)
        }
        else
        {
            holder.todotitle.setChecked(false)
        }

        if(holder.priority.text == "Low")
            holder.priority.setBackgroundResource(R.color.light_green)

        if(holder.priority.text == "Medium")
            holder.priority.setBackgroundResource(R.color.light_blue)

        if(holder.priority.text == "High")
            holder.priority.setBackgroundResource(R.color.light_orange)
    }

    override fun getItemCount(): Int {
        return alltodos.size
    }

    fun updatelist(newlist: List<Todo>) {
        alltodos.clear()

        newlist.sortedWith(ComparePriority).reversed().forEach { todo ->
            if(todo.isCompleted != true)
                alltodos.add(todo)
        }

        notifyDataSetChanged()
    }


}

interface IListenerMain {
    fun onDeleteClicked(todo: Todo)
    fun onEditClicked(view: View, todo: Todo)
    fun onTodoCompleted(todo: Todo)
}

