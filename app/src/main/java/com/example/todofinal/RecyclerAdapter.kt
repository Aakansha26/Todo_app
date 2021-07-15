package com.example.todofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val listenermain: IListenerMain):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var alltodos =  ArrayList<Todo>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val todotitle = itemView.findViewById<CheckBox>(R.id.todotitle_cb)
        val todomsg = itemView.findViewById<TextView>(R.id.todomsg_text)
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
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = alltodos[position]
        holder.todotitle.text = todo.todotitle
        holder.todomsg.text = todo.todomsg
    }

    override fun getItemCount(): Int {
        return alltodos.size
    }

    fun updatelist(newlist: List<Todo>) {
        alltodos.clear()
        alltodos.addAll(newlist)

        notifyDataSetChanged()
    }


}

interface IListenerMain {
    fun onDeleteClicked(todo: Todo)
    fun onEditClicked(view: View, todo: Todo)
}

