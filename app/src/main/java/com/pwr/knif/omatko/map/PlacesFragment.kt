package com.pwr.knif.omatko.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import com.pwr.knif.omatko.XmlPraser

class PlacesFragment : Fragment() {

    companion object {
        const val TYPE = "PLACES_TAB_TYPE"
    }

    private lateinit var tabType: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_place_list, container, false)

        tabType = arguments.getString(TYPE)


        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = PlacesRecyclerViewAdapter(
                        XmlPraser().getPlacesFromFile(activity).filter { it.category == tabType },
                        activity as MapOpener)
            }
        }
        return view
    }
}
