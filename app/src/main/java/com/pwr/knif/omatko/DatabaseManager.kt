package com.pwr.knif.omatko

import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import com.pwr.knif.omatko.EventsFragment.Companion.database


class DatabaseManager(private var context : Context){

    fun databaseConnection() {
        database = Room.databaseBuilder(context, Database::class.java, "appDatabase").build()
    }

    class GetDataFromDatabase : AsyncTask<String, Void, List<Event>>() {

        override fun doInBackground(vararg params: String?): List<Event> {
            return database.eventDao().getAllEvents() //getEventsBasedOnDayAndType(params[0]!!,params[1]!!)
        }

    }

    class AddDataToDatabase : AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event?): Boolean {
            database.eventDao().insertEvents(params.toList())
            return true
        }

    }

}