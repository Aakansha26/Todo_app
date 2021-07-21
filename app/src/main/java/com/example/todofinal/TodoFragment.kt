package com.example.todofinal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.todofinal.databinding.FragmentTodoBinding
import kotlin.jvm.internal.MagicApiIntrinsics


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

        val spinner = binding.spinner

        ArrayAdapter.createFromResource(
            mainActivity,
            R.array.priority_choices,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()

            }

        }

        binding.button.setOnClickListener { view:View ->
            val todotitle = binding.editTextTodoTitle.text.toString()
            val todomsg = binding.editTextTodoMsg.text.toString()
            val priority_value = spinner.selectedItem.toString()

            val updatedTodo = sharedViewModel.currentTodo.value
            if ( updatedTodo == null) {
                if(todotitle.isNotEmpty()) {
                    sharedViewModel.insertTodo(Todo(todotitle, todomsg, priority_value))
                    Log.i("Todofragment1", "todo created")
                }
                else {
                    Toast.makeText(activity, "Todo Title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            else {

                Log.i("Todofragment1", updatedTodo.toString())
                updatedTodo?.todotitle = todotitle
                updatedTodo?.todomsg = todomsg
                updatedTodo?.priority = priority_value
                updatedTodo?. let {
                    if(todotitle.isNotEmpty()) {
                        sharedViewModel.updateTodo(updatedTodo!!)
                    }
                    else {
                        Toast.makeText(activity, "Todo Title cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }


            }
            

            hideKeyboard(mainActivity)
            view.findNavController().navigate(R.id.action_todoFragment_to_mainFragment)
            if (updatedTodo == null)
                Toast.makeText(activity, "Todo Added Successfully!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(activity, "Todo Updated Successfully!", Toast.LENGTH_SHORT).show()


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
        if(sharedViewModel.currentTodo.value == null)
            mainActivity.supportActionBar?.title = "Create New Todo"
        else
            mainActivity.supportActionBar?.title = "Update Todo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.resetCurrentTodo()
    }


}