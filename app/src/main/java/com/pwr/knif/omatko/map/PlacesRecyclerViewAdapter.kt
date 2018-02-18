package com.pwr.knif.omatko.map

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.R
import kotlinx.android.synthetic.main.fragment_place.view.*

class PlacesRecyclerViewAdapter(
        private val placeList: List<Place>,
        private val mapOpener: MapOpener
) : RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]

        holder.fill(place)

        holder.placeView.iv_map_link.setOnClickListener {
            mapOpener.openMap(place.location, place.title)
        }
    }

    override fun getItemCount(): Int = placeList.size

    inner class ViewHolder(var placeView: View) : RecyclerView.ViewHolder(placeView) {
        var place: Place? = null

        fun fill(place: Place) {
            this.place = place

            placeView.apply {
                tv_place_title.text = place.title
                tv_place_description.text = place.description
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + place?.title + "'"
        }
    }
}
