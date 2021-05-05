package com.v1.Tammeni.Activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class liveDataAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val list: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return 2
    }


}