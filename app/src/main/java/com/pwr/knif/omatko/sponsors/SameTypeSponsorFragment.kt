package com.pwr.knif.omatko.sponsors

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import com.pwr.knif.omatko.XmlPraser

class SameTypeSponsorFragment : Fragment() {

    private lateinit var typeOfSponsor: String

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sponsors_list, container, false)

        typeOfSponsor = arguments.getString(TypeOfSponsor.KEY)

        if (view is RecyclerView) {
            val sponsorList = XmlPraser().getSponsorsFromFile(activity, typeOfSponsor)
            view.adapter = SponsorsRecyclerViewAdapter(sponsorList, context)

        }
        return view
    }

}
