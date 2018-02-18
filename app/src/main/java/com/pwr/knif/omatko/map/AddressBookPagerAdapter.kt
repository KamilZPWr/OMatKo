package com.pwr.knif.omatko.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.pwr.knif.omatko.R

class AddressBookPagerAdapter(
        manager: FragmentManager,
        activity: AppCompatActivity,
        private val addressBookTabTypes: List<AddressBookTabType>
) : FragmentPagerAdapter(manager) {

    private val tabNames = activity.resources.getStringArray(R.array.address_book_tab_names).let {
        arr ->
        addressBookTabTypes.map { type -> arr[type.ordinal] }
    }

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        bundle.putString(PlacesFragment.TYPE, addressBookTabTypes[position].toString())

        return PlacesFragment().apply { arguments = bundle }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabNames[position]
    }

    override fun getCount(): Int = addressBookTabTypes.size
}