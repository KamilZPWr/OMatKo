package com.pwr.knif.omatko

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(
        entities = arrayOf(Event::class),
        version = 1,
        exportSchema = false
)

abstract class Database : RoomDatabase() {

    abstract fun eventDao() : EventDao

}