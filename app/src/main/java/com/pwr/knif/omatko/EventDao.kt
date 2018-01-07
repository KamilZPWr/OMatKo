package com.pwr.knif.omatko

import android.arch.persistence.room.*

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents() : List<Event>

    @Query("SELECT * FROM events WHERE day = :currentDay AND type = :scheduleType")
    fun getEventsBasedOnDayAndType(currentDay : String, scheduleType : String ) : List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events : List<Event?>)

    @Delete()
    fun deleteEvent(events : List<Event?>)

}