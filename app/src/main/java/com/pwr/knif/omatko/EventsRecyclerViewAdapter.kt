package com.pwr.knif.omatko

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
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

class EventsRecyclerViewAdapter(val eventList: List<Event>, val context: Context, val activity:Activity) :
        RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_scheduleevent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        holder.fill(event)
        holder.eventView.setOnLongClickListener {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)!=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CALENDAR),123)
            }
            else {
                event.isChecked = !event.isChecked

                if (event.eventCalendarID==null) {
                    holder.addEventToCalendar(context, activity, event)
                }else{
                    holder.deleteEventInCalendar(context,activity,event)
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
        var event: Event? = null

        fun fill(event: Event) {
            this.event = event

            eventView.apply {
                tv_event_title.setText(event.title)
                tv_event_presenter.setText(event.presenter)
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
                        if (event.showLongDescription) {
                            event.longDescription
                        } else {
                            event.shortDescription
                        }
            }
        }

        @SuppressLint("MissingPermission")
        fun addEventToCalendar(context: Context, activity: Activity, event: Event) {

            fun findPhoneCalendar():Long{

                val eventProjection = arrayOf(CalendarContract.Calendars._ID)
                val projectionIdIndex = 0
                var calID: Long? = null
                val queryCursor: Cursor
                val contentResolver = activity.contentResolver
                val uri = CalendarContract.Calendars.CONTENT_URI
                val selection: String = "(" + CalendarContract.Calendars.ACCOUNT_NAME + " = ? )"
                val selectionArgs = arrayOf("Phone")

                queryCursor = contentResolver.query(uri, eventProjection, selection, selectionArgs, null)

                while (queryCursor.moveToNext()) {
                    calID = queryCursor.getLong(projectionIdIndex)
                }

                queryCursor.close()
                return calID!!
            }

            fun addReminder(eventID:Long,calendarCursor:ContentResolver ){

                val values = ContentValues()

                values.put(CalendarContract.Reminders.MINUTES, 5)
                values.put(CalendarContract.Reminders.EVENT_ID, eventID)
                values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                calendarCursor.insert(CalendarContract.Reminders.CONTENT_URI, values)
                event.eventCalendarID = eventID
            }

                val calendarID = findPhoneCalendar()
                val calendarCursor = activity.contentResolver
                val values = ContentValues()

                values.put(CalendarContract.Events.DTSTART, event.beginTime)
                values.put(CalendarContract.Events.DTEND, event.endTime)
                values.put(CalendarContract.Events.TITLE, event.title)
                values.put(CalendarContract.Events.DESCRIPTION, event.shortDescription)
                values.put(CalendarContract.Events.CALENDAR_ID, calendarID)
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "Central European Time")

                val eventUri: Uri = calendarCursor.insert(CalendarContract.Events.CONTENT_URI, values)
                val eventID = (eventUri.lastPathSegment).toLong()

                event.isChecked = true
                event.eventCalendarID = eventID


                DatabaseManager.UpdateEvent().execute(event)
                addReminder(eventID,calendarCursor)
                Toast.makeText(context,"Dodałeś do kalendarza ${event.title}",Toast.LENGTH_LONG).show()

        }

        fun deleteEventInCalendar(context: Context,activity: Activity,event: Event){

            val deleteUri:Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.eventCalendarID!!)
            activity.contentResolver.delete(deleteUri, null, null)
            Toast.makeText(context,"Usunąłeś z kalendarza ${event.title}",Toast.LENGTH_LONG).show()

            event.isChecked = false
            event.eventCalendarID = null
            DatabaseManager.UpdateEvent().execute(event)

        }
    }


}
