package com.pwr.knif.omatko

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.ContentResolver
import android.content.Context
import android.provider.CalendarContract
import com.pwr.knif.omatko.EventsFragment.Companion.database

object DatabaseManager {

    fun getEvents(day: String, type: String): List<Event> {
        return database.eventDao().getEventsBasedOnDayAndType(day, type)
    }

    fun getEventById(eventId: String): Event? {
        return database.eventDao().getEventById(eventId)
    }

    fun getOutdatedEvents(modificationTime: Long): List<Event> {
        return database.eventDao().getOutdatedEvents(modificationTime)
    }

    fun addEvents(events: List<Event?>): Boolean {
        database.eventDao().insertEvents(events)
        return true
    }

    fun updateEvent(event: Event?): Boolean {
        database.eventDao().updateEvent(event)
        return true
    }

    fun deleteEvents(events: List<Event?>): Boolean {
        database.eventDao().deleteEvents(events)
        return true
    }

    fun nukeDatabase() {
        database.eventDao().nukeDatabase()
    }

    fun databaseConnection(context: Context) {
        database = Room.databaseBuilder(context, Database::class.java, "appDatabase").build()
    }

    @SuppressLint("MissingPermission")
    fun getMaxExistingEventId(cr: ContentResolver): Long {
        val MAX_ID_COLUMN = "max_id"
        val ID = CalendarContract.Events._ID

        with(cr.query(
                CalendarContract.Events.CONTENT_URI,
                arrayOf("MAX($ID) as $MAX_ID_COLUMN"),
                null,
                null,
                ID)) {
            moveToFirst()
            val maxVal = getLong(getColumnIndex(MAX_ID_COLUMN))
            close()
            return maxVal
        }
    }
}