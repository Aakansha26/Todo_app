package com.example.todofinal.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.todofinal.adapters.PagerAdapter
import com.example.todofinal.R
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
        val pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter


        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            if(position == 0)
                tab.text = getString(R.string.tab1text)
            else
                tab.text = getString(R.string.tab2text)
        }.attach()

        return view

    }


}