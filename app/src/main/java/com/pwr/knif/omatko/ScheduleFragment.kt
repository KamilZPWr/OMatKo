package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.layout_main.*

enum class TypeOfSchedule{
    THEORETICAL, POPULARSCIENCE
}

class ScheduleFragment() : Fragment() {

    lateinit var pagerAdapter: SchedulePagerAdapter
    lateinit var scheduleFragments: List<Fragment>
    lateinit var typeOfSchedule: String


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        pagerAdapter = SchedulePagerAdapter(activity.supportFragmentManager, scheduleFragments)
        view.view_pager.adapter = pagerAdapter

        activateAppBar(activity.tab_layout, view.view_pager)


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeOfSchedule = TypeOfSchedule.valueOf(arguments.getString("TYPE_OF_SCHEDULE")).toString()

        val bundles = Array(3) { Bundle() }
        //bundles[0].putString("DAY_OF_WEEK", DayOfWeek.FRIDAY.toString())
        //bundles[1].putString("DAY_OF_WEEK", DayOfWeek.SATURDAY.toString())
        //bundles[2].putString("DAY_OF_WEEK", DayOfWeek.SUNDAY.toString())

        bundles[0].putStringArrayList("DAY_AND_TYPE", arrayListOf(DayOfWeek.FRIDAY.toString(), typeOfSchedule))
        bundles[1].putStringArrayList("DAY_AND_TYPE", arrayListOf(DayOfWeek.SATURDAY.toString() , typeOfSchedule))
        bundles[2].putStringArrayList("DAY_AND_TYPE", arrayListOf(DayOfWeek.SUNDAY.toString() , typeOfSchedule))

        scheduleFragments = bundles.map { ScheduleEventFragment().apply { arguments = it } }


    }

    fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager){
        tabLayout.setupWithViewPager(viewPager,true)
        tabLayout.getTabAt(0)!!.text = getString(R.string.day_of_week_friday)
        tabLayout.getTabAt(1)!!.text = getString(R.string.day_of_week_saturday)
        tabLayout.getTabAt(2)!!.text = getString(R.string.day_of_week_sunday)
    }
}
