package com.pwr.knif.omatko

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity

/**
 * Created by Kamil on 02.12.2017.
 */
class SchedulePagerAdapter(
        activity: AppCompatActivity,
        val days: List<DayOfWeek>,
        val typeOfSchedule: TypeOfSchedule
) : FragmentPagerAdapter(activity.supportFragmentManager) {

    val dayNames = with(activity.resources.getStringArray(R.array.days_of_week)) {
        days.map { day -> this[day.ordinal] }
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putStringArray(
                ScheduleEventFragment.DAY_AND_TYPE,
                arrayOf(
                        days[position].toString(),
                        typeOfSchedule.toString()
                )
        )

        return ScheduleEventFragment().apply { arguments = bundle }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return dayNames[position]
    }

    override fun getItemPosition(`object`: Any?): Int {
        //TODO: This is the base implementation, make it work somehow?
        return PagerAdapter.POSITION_UNCHANGED
    }

    override fun getCount(): Int = days.size
}