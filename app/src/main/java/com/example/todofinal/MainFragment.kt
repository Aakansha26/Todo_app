package com.example.todofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todofinal.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater,R.layout.fragment_main, container, false)

        val todolist: ArrayList<Todo> = ArrayList();
        todolist.add(Todo("Get vegetables", "ask mom"))
        todolist.add(Todo("eat fruits", "ask mom"))
        todolist.add(Todo("study", "go to library"))

        //Initialising recycler adapter
        val recyclerAdapter = RecyclerAdapter(todolist)
        binding.recyclerView.apply {
            adapter = recyclerAdapter
//            addItemDecoration(DividerItemDecoration(this@, DividerItemDecoration.VERTICAL))
        }

        binding.floatingActionButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_todoFragment)
        }
        return binding.root

    }



}