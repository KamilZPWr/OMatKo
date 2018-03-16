package com.pwr.knif.omatko.sponsors

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_sponsors.view.*
import kotlinx.android.synthetic.main.layout_main.*

enum class TypeOfSponsor {
    SPONSOR, SUPPORTING_COMPANY, SPONSORSHIP, MEDIA, ORGANIZER;

    companion object {
        const val KEY = "TYPE_OF_SPONSOR"
    }
}

class AllSponsorsFragment : Fragment() {

    private lateinit var pagerAdapter: SponsorsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sponsors, container, false)

        pagerAdapter = SponsorsPagerAdapter(
                childFragmentManager,
                activity as AppCompatActivity,
                listOf(
                        TypeOfSponsor.SPONSOR,
                        TypeOfSponsor.SUPPORTING_COMPANY,
                        TypeOfSponsor.SPONSORSHIP,
                        TypeOfSponsor.MEDIA,
                        TypeOfSponsor.ORGANIZER
                )
        )
        view.view_pager_sponsors.adapter = pagerAdapter
        activateAppBar(activity.tab_layout, view.view_pager_sponsors)

        return view
    }

    private fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager, true)
    }
}