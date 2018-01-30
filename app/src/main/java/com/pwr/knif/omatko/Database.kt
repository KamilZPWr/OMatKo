package com.pwr.knif.omatko

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

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