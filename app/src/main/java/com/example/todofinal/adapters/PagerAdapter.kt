package com.example.todofinal.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todofinal.screens.CompletedTodosFragment
import com.example.todofinal.screens.MainFragment

class PagerAdapter(fm: Fragment): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            MainFragment()
        } else {
            CompletedTodosFragment()
        }
    }
}