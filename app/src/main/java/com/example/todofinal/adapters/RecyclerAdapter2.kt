package com.example.todofinal

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todofinal.screens.Todo

class RecyclerAdapter2(val listenerCompletedTodo: IListenerCompletedTodo):
    RecyclerView.Adapter<RecyclerAdapter2.ViewHolder2>() {

    var alltodos =  ArrayList<Todo>()

    inner class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView) {
        val todotitle = itemView.findViewById<CheckBox>(R.id.todotitle_cb)
        val todomsg = itemView.findViewById<TextView>(R.id.todomsg_text)
        val priority = itemView.findViewById<TextView>(R.id.priority_tv)
        val delete_button = itemView.findViewById<ImageView>(R.id.delete_button)
        val edit_button = itemView.findViewById<ImageView>(R.id.edit_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ViewHolder2(layoutInflater.inflate(R.layout.todo, parent, false))
        viewHolder.delete_button.setOnClickListener{
            listenerCompletedTodo.onDeleteClicked(alltodos[viewHolder.adapterPosition])
        }

        viewHolder.edit_button.setOnClickListener{ view: View ->
            listenerCompletedTodo.onEditClicked(view, alltodos[viewHolder.adapterPosition])

        }

        viewHolder.todotitle.setOnClickListener() {
            viewHolder.todotitle.setChecked(false)
            Handler(Looper.getMainLooper()).postDelayed({
                listenerCompletedTodo.onTodoIncompleted(alltodos[viewHolder.adapterPosition])
            }, 1200)


        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return alltodos.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter2.ViewHolder2, position: Int) {
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

    fun updatelist(newlist: List<Todo>) {
        alltodos.clear()
        newlist.sortedWith(ComparePriority).reversed().forEach { todo ->
            if(todo.isCompleted == true)
                alltodos.add(todo)
        }
        notifyDataSetChanged()
    }


}

interface IListenerCompletedTodo {
    fun onDeleteClicked(todo: Todo)
    fun onEditClicked(view: View, todo: Todo)
    fun onTodoIncompleted(todo: Todo)

}