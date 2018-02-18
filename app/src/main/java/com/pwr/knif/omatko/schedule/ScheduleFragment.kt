package com.pwr.knif.omatko.schedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.DayOfWeek
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.layout_main.*

enum class TypeOfSchedule {
    THEORETICAL, POPULARSCIENCE;

    companion object {
        const val KEY = "TYPE_OF_SCHEDULE"
    }
}

class ScheduleFragment : Fragment() {

    private lateinit var pagerAdapter: SchedulePagerAdapter
    private lateinit var typeOfSchedule: TypeOfSchedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeOfSchedule = TypeOfSchedule.valueOf(arguments.getString(TypeOfSchedule.KEY))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        pagerAdapter = SchedulePagerAdapter(
                childFragmentManager,
                activity as AppCompatActivity,
                listOf(
                        DayOfWeek.FRIDAY,
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                ),
                typeOfSchedule
        )

        view.view_pager_schedule.adapter = pagerAdapter
        activateAppBar(activity.tab_layout, view.view_pager_schedule)

        return view
    }

    private fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.setupWithViewPager(viewPager, true)
    }
}
