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

                events.children.forEach { eventSnapshot ->
                    val oldEvent = DatabaseManager.getEventById(eventSnapshot.key.toString())

                    fun childValue(name: String): String = eventSnapshot.child(name).value.toString()
                    if (oldEvent != null) {
                        Log.e("FB", "Update")
                        val newEvent = Event(
                                eventSnapshot.key.toString(),
                                childValue("title"),
                                childValue("presenter"),
                                childValue("place"),
                                childValue("shortDescription"),
                                childValue("longDescription"),
                                childValue("beginTime").toLong(),
                                childValue("endTime").toLong(),
                                childValue("type"),
                                childValue("day"),
                                modificationTime,
                                oldEvent.isChecked,
                                oldEvent.showLongDescription,
                                oldEvent.eventCalendarID
                        )

                        DatabaseManager.updateEvent(newEvent)
                    } else {
                        val newEvent = Event(
                                eventSnapshot.key.toString(),
                                childValue("title"),
                                childValue("presenter"),
                                childValue("place"),
                                childValue("shortDescription"),
                                childValue("longDescription"),
                                childValue("beginTime").toLong(),
                                childValue("endTime").toLong(),
                                childValue("type"),
                                childValue("day"),
                                modificationTime
                        )

                        DatabaseManager.addEvents(listOf(newEvent))
                    }

                    val eventsToDelete = DatabaseManager.getEventsByLastModification(modificationTime)
                    DatabaseManager.deleteEvents(eventsToDelete)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO: do sth here
            }
        })
    }
}