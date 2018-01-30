package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_place.view.*

class PlacesRecyclerViewAdapter(
        private val placeList: List<Place>
) : RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]

        holder.fill(place)
        holder.placeView.setOnClickListener {
            holder.toggle()
        }
    }

    override fun getItemCount(): Int = placeList.size

    inner class ViewHolder(var placeView: View) : RecyclerView.ViewHolder(placeView) {
        private var place: Place? = null

        fun fill(place: Place) {
            this.place = place

            placeView.apply {
                tv_place_title.text = place.title
                tv_place_description.text = place.description
            }
        }

        fun toggle() {
            with(placeView) {
                tv_place_description.visibility
                if (tv_place_description.visibility == View.VISIBLE)
                    View.GONE
                else
                    View.VISIBLE
                btn_map_link.visibility =
                        if (btn_map_link.visibility == View.VISIBLE)
                            View.GONE
                        else
                            View.VISIBLE
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + place?.title + "'"
        }

    }

}
