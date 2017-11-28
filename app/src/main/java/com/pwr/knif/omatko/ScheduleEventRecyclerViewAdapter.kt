package com.pwr.knif.omatko

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_scheduleevent.view.*

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

        fun fill():Boolean{
            holder.fill(event)
            return true
        }

        fill()

        holder.eventView.setOnLongClickListener {
            listener?.onListFragmentInteraction(holder.scheduleEvent!!)
            event.isTicked = !event.isTicked
            fill()
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
                tv_event_description.setText(event.shortDescription)

                if (event.isTicked){
                    ticket_event.setBackgroundResource(R.drawable.background_ticked_event)
                }else{
                    ticket_event.setBackgroundResource(R.drawable.background_not_ticked_event)
                }
            }
        }

        override fun toString(): String {
            return super.toString() + "OK"
        }
    }

}
