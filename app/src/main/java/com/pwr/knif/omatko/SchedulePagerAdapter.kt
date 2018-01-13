package com.pwr.knif.omatko

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity

class SchedulePagerAdapter(
        manager: FragmentManager,
        activity: AppCompatActivity,
        private val days: List<DayOfWeek>,
        private val typeOfSchedule: TypeOfSchedule
) : FragmentPagerAdapter(manager) {

    private val dayNames = with(activity.resources.getStringArray(R.array.days_of_week)) {
        days.map { day -> this[day.ordinal] }
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putStringArray(
                EventsFragment.DAY_AND_TYPE,
                arrayOf(
                        days[position].toString(),
                        typeOfSchedule.toString()
                )
        )

        return EventsFragment().apply { arguments = bundle }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return dayNames[position]
    }

    override fun getCount(): Int = days.size
}