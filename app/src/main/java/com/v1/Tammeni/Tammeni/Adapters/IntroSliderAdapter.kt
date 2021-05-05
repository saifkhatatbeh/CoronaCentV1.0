package com.v1.Tammeni.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class myPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    val list: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

}