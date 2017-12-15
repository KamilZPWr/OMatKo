package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_scheduleevent.view.*

class ScheduleEventRecyclerViewAdapter(val eventList: List<ScheduleEvent>) :
        RecyclerView.Adapter<ScheduleEventRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_scheduleevent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        holder.fill(event)

        holder.eventView.setOnLongClickListener {
            event.isChecked = !event.isChecked
            holder.update()
            true
        }

        holder.eventView.setOnClickListener {
            event.showLongDescription = !event.showLongDescription
            holder.update()
        }
    }

    override fun getItemCount() = eventList.size

    class ViewHolder(var eventView: View) : RecyclerView.ViewHolder(eventView) {
        var scheduleEvent: ScheduleEvent? = null

        fun fill(event: ScheduleEvent) {
            scheduleEvent = event

            eventView.apply {
                tv_event_title.setText(event.title)
                tv_event_presenter.setText(event.presenter)
            }
            update()
        }

        fun update() {
            val event = scheduleEvent!!
            eventView.apply {
                ticket_event.setBackgroundResource(
                        if (event.isChecked)
                            R.drawable.background_ticked_event
                        else
                            R.drawable.background_not_ticked_event)

                tv_event_description.text =
                        if (event.showLongDescription) {
                            event.longDescription
                        } else {
                            event.shortDescription
                        }
            }
        }

        override fun toString(): String {
            return super.toString()
        }
    }

}
