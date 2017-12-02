package com.pwr.knif.omatko

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Kamil on 02.12.2017.
 */
class SchedulePagerAdapter(fragmentManager: FragmentManager, scheduleFragments: ArrayList<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {
    val fragments = scheduleFragments

    override fun getItem(position: Int): Fragment {
        return fragments[position]
   }

    override fun getCount(): Int = fragments.size
}