package com.example.todofinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.todofinal.databinding.FragmentMainBinding


class MainFragment : Fragment(), IListenerMain {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater,R.layout.fragment_main, container, false)
        mainActivity = this.activity as MainActivity

        //Initialising recycler adapter
        val recyclerAdapter = RecyclerAdapter(this)
        binding.recyclerView.apply {
            adapter = recyclerAdapter
//            addItemDecoration(DividerItemDecoration(this@, DividerItemDecoration.VERTICAL))
        }

        sharedViewModel.allTodos.observe(viewLifecycleOwner, { list ->
            list ?.let {
                recyclerAdapter.updatelist(it)
            }
        })


        binding.floatingActionButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_todoFragment)
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        mainActivity.supportActionBar?.title = "Todo App"
    }

    override fun onDeleteClicked(todo: Todo) {
        sharedViewModel.deleteTodo(todo)
    }

    override fun onEditClicked(view: View, todo: Todo) {
        view.findNavController().navigate(R.id.action_mainFragment_to_todoFragment)
        sharedViewModel.changeCurrentTodo(todo)
    }


}