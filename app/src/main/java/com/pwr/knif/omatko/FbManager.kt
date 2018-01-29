package com.pwr.knif.omatko

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import java.util.*
import kotlin.collections.ArrayList

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

        fbDatabase.child("results").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(resultsSnapshot: DataSnapshot) {

                val modificationTime = Calendar.getInstance().timeInMillis

                resultsSnapshot.children.forEach {

                    fun getAllVoters(votesSnapshot: DataSnapshot): ArrayList<String> {

                        val voters = arrayListOf<String>()

                        votesSnapshot.children.forEach {
                            voters.add(it.key.toString())
                        }
                        return voters
                    }

                    val newResult = Result(it.key.toString(), getAllVoters(it), modificationTime)

                    doAsync { DatabaseManager.insertResults(listOf(newResult)) }

                    val resultsToDelete = doAsyncResult { DatabaseManager.getOutdatedResults(modificationTime) }.get()

                    doAsync { DatabaseManager.deleteResults(resultsToDelete) }

                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }
}