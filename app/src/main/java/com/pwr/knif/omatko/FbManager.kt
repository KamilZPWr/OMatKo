package com.pwr.knif.omatko

import android.util.Log
import com.google.firebase.database.*
import org.jetbrains.anko.doAsync
import java.util.*

object FbManager {

    private var fbDatabase = FirebaseDatabase.getInstance().reference!!

    fun activateValueListener() {

        fbDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val events = dataSnapshot.child("events")
                val modificationTime = Calendar.getInstance().timeInMillis

                events.children.forEach {
                    doAsync {
                        val oldEvent = DatabaseManager.getEventById(it.key.toString())

                        if (oldEvent != null) {
                            Log.e("FB","Update")
                            val newEvent = Event(it.key.toString(),
                                    it.child("title").value.toString(),
                                    it.child("presenter").value.toString(),
                                    it.child("place").value.toString(),
                                    it.child("shortDescription").value.toString(),
                                    it.child("longDescription").value.toString(),
                                    it.child("beginTime").value.toString().toLong(),
                                    it.child("endTime").value.toString().toLong(),
                                    it.child("type").value.toString(),
                                    it.child("day").value.toString(),
                                    modificationTime,
                                    oldEvent.isChecked,
                                    oldEvent.showLongDescription,
                                    oldEvent.eventCalendarID)

                            DatabaseManager.updateEvent(newEvent)
                        }
                        else{
                            val newEvent = Event(it.key.toString(),
                                    it.child("title").value.toString(),
                                    it.child("presenter").value.toString(),
                                    it.child("place").value.toString(),
                                    it.child("shortDescription").value.toString(),
                                    it.child("longDescription").value.toString(),
                                    it.child("beginTime").value.toString().toLong(),
                                    it.child("endTime").value.toString().toLong(),
                                    it.child("type").value.toString(),
                                    it.child("day").value.toString(),
                                    modificationTime)

                            DatabaseManager.addEvents(listOf(newEvent))
                        }

                        val eventsToDelete = DatabaseManager.getEventsByLastModification(modificationTime)
                        DatabaseManager.deleteEvents(eventsToDelete)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO: do sth here
            }
        })
    }
}