package com.example.todofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.todofinal.databinding.FragmentMainBinding
import com.example.todofinal.databinding.FragmentTodoBinding


class TodoFragment : Fragment() {


    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTodoBinding>(inflater,R.layout.fragment_todo, container, false)


        binding.button.setOnClickListener {
            val todotitle = binding.editTextTodoTitle.text.toString()
            val todomsg = binding.editTextTodoMsg.text.toString()

            if(todotitle.isNotEmpty()) {
                sharedViewModel.insertTodo(Todo(todotitle, todomsg))
            }
        }

        return binding.root
    }

}