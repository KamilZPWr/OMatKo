package com.pwr.knif.omatko

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_address_book.view.*
import kotlinx.android.synthetic.main.layout_main.*

enum class AddressBookTabType {
    OMatKo, FOOD, SIGHTS, ENTERTAINMENT, SHOPPING_AND_STATIONS;

    companion object {
        const val KEY = "TYPE_OF_ADDRESS_BOOK"
    }
}

class AddressBookFragment : Fragment() {

    private lateinit var pagerAdapter: AddressBookPagerAdapter
    private val addressBookTabTypes = AddressBookTabType.values().toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_address_book, container, false)
        pagerAdapter = AddressBookPagerAdapter(
                childFragmentManager,
                activity as AppCompatActivity,
                addressBookTabTypes
        )

        view.view_pager_address_book.adapter = pagerAdapter
        activateAppBar(activity.tab_layout, view.view_pager_address_book)

        return view
    }

    private fun activateAppBar(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager, true)
    }
}
