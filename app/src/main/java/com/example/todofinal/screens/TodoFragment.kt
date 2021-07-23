package com.example.todofinal.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.todofinal.*
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
        binding = DataBindingUtil.inflate<FragmentTodoBinding>(inflater,
            R.layout.fragment_todo, container, false)
        mainActivity = this.activity as MainActivity

        val spinner = binding.spinner

        val items= resources.getStringArray(R.array.priority_choices)
        val spinnerAdapter= object : ArrayAdapter<String>(mainActivity,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position > 0) {
                    val type = parent?.getItemAtPosition(position).toString()
                }
                else
                {
                    (view as TextView).setTextColor(Color.GRAY)
                }

            }

        }

        binding.button.setOnClickListener { view:View ->
            val todotitle = binding.editTextTodoTitle.text.toString()
            val todomsg = binding.editTextTodoMsg.text.toString()
            val priority_value = spinner.selectedItem.toString()

            if(todotitle.isEmpty())
            {
                Toast.makeText(activity, getString(R.string.todo_title_empty), Toast.LENGTH_SHORT).show()
            }
            else if(priority_value == "Priority")
            {
                Toast.makeText(activity, getString(R.string.please_select_priority), Toast.LENGTH_SHORT).show()
            }
            else
            {
                val updatedTodo = sharedViewModel.currentTodo.value
                if ( updatedTodo == null) {
                    sharedViewModel.insertTodo(Todo(todotitle, todomsg, priority_value, false))
                    Toast.makeText(activity, getString(R.string.todo_added), Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_todoFragment_to_viewPagerFragment)
                }
                else {

                    updatedTodo?.todotitle = todotitle
                    updatedTodo?.todomsg = todomsg
                    updatedTodo?.priority = priority_value
                    updatedTodo?. let {
                        sharedViewModel.updateTodo(updatedTodo!!)
                        Toast.makeText(activity, getString(R.string.todo_updated), Toast.LENGTH_SHORT).show()
                        view.findNavController().navigate(R.id.action_todoFragment_to_viewPagerFragment)
                    }
                }
            }


            hideKeyboard(mainActivity)


        }


        sharedViewModel.currentTodo.observe(viewLifecycleOwner, { todo ->

            todo ?. let {
                binding.editTextTodoTitle.setText(todo.todotitle)
                binding.editTextTodoMsg.setText(todo.todomsg)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(sharedViewModel.currentTodo.value == null)
            mainActivity.supportActionBar?.title = getString(R.string.create_new_todo)
        else
            mainActivity.supportActionBar?.title = getString(R.string.update_todo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.resetCurrentTodo()
    }


}