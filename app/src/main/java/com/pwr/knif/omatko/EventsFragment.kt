package com.pwr.knif.omatko

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EventsFragment : Fragment() {

    companion object {
        @JvmStatic
        val DAY_AND_TYPE = "DAY_AND_TYPE"
        lateinit var database : Database
    }

    lateinit var dayAndType: Array<String>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_scheduleevent_list, container, false)

        dayAndType = arguments.getStringArray(DAY_AND_TYPE)

        if (view is RecyclerView) {
            val context = view.context
            view.layoutManager = LinearLayoutManager(context)
            val eventsList = DatabaseManager.GetDataFromDatabase().execute(dayAndType[0], dayAndType[1]).get()
            view.adapter = EventsRecyclerViewAdapter(eventsList,context,activity)
        }
        return view
    }
}