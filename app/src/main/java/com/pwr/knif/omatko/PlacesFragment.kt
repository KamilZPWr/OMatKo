package com.pwr.knif.omatko

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng

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

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = PlacesRecyclerViewAdapter(
                        listOf(
                            Place("miejsce 1", "opis1", LatLng(-2.43, 50.4521)),
                            Place("miejsce 2", "opis2", LatLng(37.13123, 50.4521)),
                            Place("miejsce 3", "opis3", LatLng(-2.43, 88.755))
                        ),
                        activity as MapOpener)
            }
        }

        return view
    }

}
