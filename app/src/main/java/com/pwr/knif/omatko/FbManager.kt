package com.pwr.knif.omatko

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import java.util.*

object FbManager {

    private var fbDatabase = FirebaseDatabase.getInstance().reference!!

    fun activateValueListener() {

        fbDatabase.child("events").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(eventsSnapshot: DataSnapshot) {

                val modificationTime = Calendar.getInstance().timeInMillis

                eventsSnapshot.children.forEach { eventSnapshot ->
                    val oldEvent = doAsyncResult { DatabaseManager.getEventById(eventSnapshot.key.toString()) }.get()

                    fun childValue(name: String): String = eventSnapshot.child(name).value.toString()
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

                    if (oldEvent != null) {
                        Log.e("FB", "Update")
                        newEvent.apply {
                            isChecked = oldEvent.isChecked
                            showLongDescription = oldEvent.showLongDescription
                            eventCalendarID = oldEvent.eventCalendarID
                        }

                        doAsync { DatabaseManager.updateEvent(newEvent) }
                    } else {
                        doAsync { DatabaseManager.addEvents(listOf(newEvent)) }
                    }

                    val eventsToDelete = doAsyncResult { DatabaseManager.getOutdatedEvents(modificationTime) }.get()
                    doAsync { DatabaseManager.deleteEvents(eventsToDelete) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO: error handling
            }
        })
    }
}