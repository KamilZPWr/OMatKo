package com.pwr.knif.omatko

import android.arch.persistence.room.Room
import android.content.Context
import com.pwr.knif.omatko.EventsFragment.Companion.database

object DatabaseManager {

    fun getEvents(day: String, type: String): List<Event> {
        return database.eventDao().getEventsBasedOnDayAndType(day, type)
    }

    fun getEventById(eventId: String): Event? {
        return database.eventDao().getEventById(eventId)
    }

    fun getEventsByLastModification(modificationTime: Long): List<Event> {
        return database.eventDao().getEventsByLastModification(modificationTime)
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
}