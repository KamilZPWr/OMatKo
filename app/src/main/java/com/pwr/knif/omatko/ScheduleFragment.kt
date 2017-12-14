package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
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
    lateinit var typeOfSchedule: String
    var dayAndType = mutableListOf<String>("Day","Type")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        //typeOfSchedule = TypeOfSchedule.valueOf(arguments.getString("TYPE_OF_SCHEDULE")).toString()

        pagerAdapter = SchedulePagerAdapter(activity.supportFragmentManager, dayAndType)
        view.view_pager.adapter = pagerAdapter
        activateAppBar(activity.tab_layout, view.view_pager)
        Log.d("TAG", "onCreateView sf")

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("TAG", "onCreate")

    }

    fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager){
        tabLayout.setupWithViewPager(viewPager,true)
        tabLayout.getTabAt(0)!!.text = getString(R.string.day_of_week_friday)
        tabLayout.getTabAt(1)!!.text = getString(R.string.day_of_week_saturday)
        tabLayout.getTabAt(2)!!.text = getString(R.string.day_of_week_sunday)
    }
}
