package com.example.todofinal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.todofinal.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {


    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentTodoBinding>(inflater,R.layout.fragment_todo, container, false)
        mainActivity = this.activity as MainActivity

        binding.button.setOnClickListener { view:View ->
            val todotitle = binding.editTextTodoTitle.text.toString()
            val todomsg = binding.editTextTodoMsg.text.toString()

            Log.i("Todofragment1", "hii")
            if (sharedViewModel.currentTodo.value == null) {
                if(todotitle.isNotEmpty()) {
                    sharedViewModel.insertTodo(Todo(todotitle, todomsg))
                    Log.i("Todofragment1", "todo created")
                }
                else {

                }
            }
            else {
                val updatedTodo = sharedViewModel.currentTodo.value
                Log.i("Todofragment1", updatedTodo.toString())
                updatedTodo?.todotitle = todotitle
                updatedTodo?.todomsg = todomsg
                updatedTodo?. let {
                    if(todotitle.isNotEmpty()) {
                        sharedViewModel.updateTodo(updatedTodo)
                    }
                }


            }
            

            hideKeyboard(mainActivity)
            view.findNavController().navigate(R.id.action_todoFragment_to_mainFragment)
            Toast.makeText(activity, "Todo Added Successfully!", Toast.LENGTH_SHORT).show()
        }


        sharedViewModel.currentTodo.observe(viewLifecycleOwner, { todo ->

            todo ?. let {
                binding.editTextTodoTitle.setText(todo.todotitle)
                binding.editTextTodoMsg.setText(todo.todomsg)
                Log.i("Todofragment1", todo.toString())
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainActivity.supportActionBar?.title = "Create New Todo"
    }


}