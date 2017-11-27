package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_scheduleevent.view.*

/**
 * Created by Kamil on 27.11.2017.
 */
class ScheduleEventRecyclerViewAdapter(
        val eventList: List<ScheduleEvent>,
        val listener: ScheduleEventFragment.OnScheduleEventListFragmentInteractionListener?
) : RecyclerView.Adapter<ScheduleEventRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_scheduleevent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        holder.fill(event)

        holder.eventView.setOnClickListener {
            listener?.onListFragmentInteraction(holder.scheduleEvent!!)
        }
    }

    override fun getItemCount() = eventList.size

    class ViewHolder(var eventView: View) : RecyclerView.ViewHolder(eventView) {
        var scheduleEvent: ScheduleEvent? = null

        fun fill(event: ScheduleEvent) {
            scheduleEvent = event
            eventView.apply {
                // TODO: załadować zawartość
                textView.setText("OK")
            }
        }

        override fun toString(): String {
            return super.toString() + "OK"
        }
    }
}
