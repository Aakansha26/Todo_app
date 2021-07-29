package com.example.todofinal.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.todofinal.adapters.PagerAdapter
import com.example.todofinal.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        val pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            if(position == 0)
            {
                tab.text = getString(R.string.tab1text)
                tab.setIcon(R.drawable.ic_baseline_cancel_24)
            }
            else
            {
                tab.text = getString(R.string.tab2text)
                tab.setIcon(R.drawable.ic_baseline_library_add_check_24)
            }

        }.attach()

        fab.setOnClickListener { v:View ->
            v.findNavController().navigate(R.id.action_viewPagerFragment_to_todoFragment)
        }

        return view

    }


}