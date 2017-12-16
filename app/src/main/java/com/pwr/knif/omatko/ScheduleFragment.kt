package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.layout_main.*

enum class TypeOfSchedule {
    THEORETICAL, POPULARSCIENCE;

    companion object {
        @JvmStatic
        val KEY = "TYPE_OF_SCHEDULE"
    }
}

class ScheduleFragment() : Fragment() {

    lateinit var pagerAdapter: SchedulePagerAdapter
    lateinit var typeOfSchedule: TypeOfSchedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeOfSchedule = TypeOfSchedule.valueOf(arguments.getString(TypeOfSchedule.KEY))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        Log.d("TAG", container.toString())
        pagerAdapter = SchedulePagerAdapter(
                childFragmentManager,
                activity as AppCompatActivity,
                listOf(
                        DayOfWeek.FRIDAY,  //.getResourceString(resources),
                        DayOfWeek.SATURDAY,//.getResourceString(resources),
                        DayOfWeek.SUNDAY   //.getResourceString(resources)
                ),
                typeOfSchedule
        )

        view.view_pager.adapter = pagerAdapter
        activateAppBar(activity.tab_layout, view.view_pager)

        return view
    }

    fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.setupWithViewPager(viewPager, true)
    }
}
