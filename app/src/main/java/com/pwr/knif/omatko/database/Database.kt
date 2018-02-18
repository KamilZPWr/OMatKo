package com.pwr.knif.omatko.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.pwr.knif.omatko.schedule.Event
import com.pwr.knif.omatko.schedule.EventDao
import com.pwr.knif.omatko.votes.Result
import com.pwr.knif.omatko.votes.ResultDao

@Database(
        entities = [(Event::class), (Result::class)],
        version = 1,
        exportSchema = false
)

@TypeConverters(Converters::class)

abstract class Database : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun resultDao(): ResultDao

}