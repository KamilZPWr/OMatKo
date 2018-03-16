package com.pwr.knif.omatko.sponsors

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.pwr.knif.omatko.R

class SponsorsPagerAdapter(manager: FragmentManager,
                           activity: AppCompatActivity,
                           private val sponsorTypes: List<TypeOfSponsor>
) : FragmentPagerAdapter(manager) {

    private val groupNames = with(activity.resources.getStringArray(R.array.type_of_sponsors_group)) {
        sponsorTypes.map { group -> this[group.ordinal] }
    }

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(TypeOfSponsor.KEY ,sponsorTypes[position].toString())

        return SameTypeSponsorFragment().apply { arguments = bundle }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return groupNames[position]
    }

    override fun getCount(): Int = sponsorTypes.size
}