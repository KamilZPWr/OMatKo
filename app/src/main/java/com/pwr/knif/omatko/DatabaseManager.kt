package com.pwr.knif.omatko

import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.pwr.knif.omatko.EventsFragment.Companion.database


class DatabaseManager(private var context : Context){

    class GetEvents : AsyncTask<String, Void, List<Event>>() {

        override fun doInBackground(vararg params: String?): List<Event> {
            return database.eventDao().getEventsBasedOnDayAndType(params[0]!!,params[1]!!)
        }

    }

    class AddEvents : AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event?): Boolean {
            database.eventDao().insertEvents(params.toList())
            return true
        }
    }

    class DeleteEvents : AsyncTask<Event, Void, Boolean>(){

        override fun doInBackground(vararg params: Event?): Boolean {
            database.eventDao().deleteEvents(params.toList())
            return true
        }
    }

    class UpdateEvent : AsyncTask<Event, Void,Boolean>(){

        override fun doInBackground(vararg params: Event?): Boolean {
            database.eventDao().updateEvent(params[0]!!)
            return true
        }
    }

    fun databaseConnection() {
        database = Room.databaseBuilder(context, Database::class.java, "appDatabase").build()
    }
}