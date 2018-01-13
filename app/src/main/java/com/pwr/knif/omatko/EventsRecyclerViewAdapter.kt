package com.pwr.knif.omatko

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_scheduleevent.view.*
import org.jetbrains.anko.doAsync
import android.content.Intent
import android.provider.CalendarContract.Events
import java.lang.ref.WeakReference

class EventsRecyclerViewAdapter(
        private val eventList: List<Event>,
        private val context: Context,
        private val activity: Activity
) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_scheduleevent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        holder.fill(event)
        holder.eventView.setOnLongClickListener {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CALENDAR), 123)
            } else {

                if (event.eventCalendarID == null) {
                    addEventToCalendar(activity, event, holder)
                } else {
                    deleteEventInCalendar(context, activity, event)
                }
                holder.update()
            }

            true
        }

        holder.eventView.setOnClickListener {
            event.showLongDescription = !event.showLongDescription
            holder.update()
        }
    }

    override fun getItemCount() = eventList.size

    class ViewHolder(var eventView: View) : RecyclerView.ViewHolder(eventView) {
        private var event: Event? = null

        fun fill(event: Event) {
            this.event = event

            eventView.apply {
                tv_event_title.text = event.title
                tv_event_presenter.text = event.presenter
            }
            update()
        }

        fun update() {
            val event = event!!
            eventView.apply {
                ticket_event.setBackgroundResource(
                        if (event.isChecked)
                            R.drawable.background_ticked_event
                        else
                            R.drawable.background_not_ticked_event)

                tv_event_description.text =
                        if (event.showLongDescription)
                            event.longDescription
                        else
                            event.shortDescription
            }
        }
    }

    companion object {

        fun addEventToCalendar(activity: Activity, event: Event, holder: ViewHolder? = null) {

            val eventId = getNewEventId(activity.contentResolver)
            val intent = Intent(Intent.ACTION_INSERT)
                    .setData(Events.CONTENT_URI)
                    .putExtra(Events._ID, eventId)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.beginTime)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.endTime)
                    .putExtra(Events.TITLE, event.title)
                    .putExtra(Events.DESCRIPTION, event.shortDescription)
                    .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                    .putExtra(Events.EVENT_TIMEZONE, "Central European Time")
            activity.startActivity(intent)

            if (activity is MainActivity) {
                if(holder != null) activity.temporaryHolder = WeakReference(holder)
                activity.temporaryId = eventId
                activity.temporaryEvent = event
            }
        }

        @SuppressLint("MissingPermission")
        private fun getNewEventId(cr: ContentResolver): Long {
            with(cr.query(
                    Events.CONTENT_URI,
                    arrayOf("MAX(${Events._ID}) as max_id"),
                    null,
                    null,
                    Events._ID)) {
                moveToFirst()
                val maxVal = getLong(getColumnIndex("max_id"))
                close()
                return maxVal + 1
            }
        }

        fun deleteEventInCalendar(context: Context, activity: Activity, event: Event) {

            val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.eventCalendarID!!)
            activity.contentResolver.delete(deleteUri, null, null)
            Toast.makeText(context, "Usunąłeś z kalendarza ${event.title}", Toast.LENGTH_LONG).show()

            event.isChecked = false
            event.eventCalendarID = null
            doAsync { DatabaseManager.updateEvent(event) }
        }
    }
}
