package com.example.todofinal.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todofinal.*
import com.example.todofinal.databinding.FragmentCompletedTodosBinding

class CompletedTodosFragment : Fragment(), IListenerCompletedTodo {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCompletedTodosBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_completed_todos, container, false)
        mainActivity = this.activity as MainActivity

        val recyclerAdapter = RecyclerAdapter2(this)
        binding.rvCompletedTodos.apply {
            adapter = recyclerAdapter
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        sharedViewModel.allTodos.observe(viewLifecycleOwner, { list ->
            list ?.let {
                recyclerAdapter.updatelist(it)
            }
        })

        binding.floatingActionButton2.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_viewPagerFragment_to_todoFragment)
        }

        return binding.root
    }

    override fun onDeleteClicked(todo: Todo) {
        sharedViewModel.deleteTodo(todo)
        Toast.makeText(activity, getString(R.string.todo_deleted), Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mainActivity.supportActionBar?.title = getString(R.string.app_name)
    }

}