package com.pwr.knif.omatko.schedule

import android.arch.persistence.room.*

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>

    @Query("SELECT * FROM events WHERE day = :currentDay AND type = :scheduleType")
    fun getEventsBasedOnDayAndType(currentDay: String, scheduleType: String): List<Event>

    @Query("SELECT * FROM events WHERE eventId = :currentEventId")
    fun getEventById(currentEventId: String): Event?

    @Query("SELECT * FROM events WHERE lastModification < :modificationTime")
    fun getOutdatedEvents(modificationTime: Long): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: List<Event?>)

    @Delete()
    fun deleteEvents(events: List<Event?>)

    @Update()
    fun updateEvent(events: Event?)

    @Query("DELETE FROM events")
    fun nukeDatabase()
}