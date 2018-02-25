package com.pwr.knif.omatko.history

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import com.pwr.knif.omatko.XmlPraser
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.layout_main.*

class HistoryFragment : Fragment() {
    private lateinit var pagerAdapter: HistoryPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val history = XmlPraser().getHistoryFromFile(activity as Activity)

        pagerAdapter = HistoryPagerAdapter(childFragmentManager,history)

        view.view_pager_history.adapter = pagerAdapter
        activateAppBar((activity as Activity).tab_layout, view.view_pager_history)

        return view
    }

    private fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager, true)
    }
}