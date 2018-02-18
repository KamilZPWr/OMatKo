package com.pwr.knif.omatko.schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pwr.knif.omatko.database.Database
import com.pwr.knif.omatko.database.DatabaseManager
import com.pwr.knif.omatko.R
import org.jetbrains.anko.doAsyncResult

class EventsFragment : Fragment() {

    companion object {
        const val DAY_AND_TYPE = "DAY_AND_TYPE"
        lateinit var database: Database
    }

    private lateinit var dayAndType: Array<String>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_scheduleevent_list, container, false)

        dayAndType = arguments.getStringArray(DAY_AND_TYPE)

        if (view is RecyclerView) {

            with(view) {
                layoutManager = LinearLayoutManager(context)
                val eventsList = doAsyncResult { DatabaseManager.getEvents(dayAndType[0], dayAndType[1]) }.get()
                adapter = EventsRecyclerViewAdapter(eventsList, context, activity)
            }

            val context = view.context
            view.layoutManager = LinearLayoutManager(context)
            var eventsList = doAsyncResult { DatabaseManager.getEvents(dayAndType[0], dayAndType[1]) }.get()
            eventsList = eventsList.sortedBy { it.beginTime }
            view.adapter = EventsRecyclerViewAdapter(eventsList, context, activity)

        }

        return view
    }

}