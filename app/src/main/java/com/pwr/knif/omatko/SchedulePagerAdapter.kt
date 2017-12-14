package com.pwr.knif.omatko

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SchedulePagerAdapter(fragmentManager: FragmentManager, dayAndType: MutableList<String>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        var fragment = ScheduleEventFragment()
        return fragment
    }

    override fun getCount(): Int = 3

}