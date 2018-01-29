package com.pwr.knif.omatko

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "results")
data class Result(

        @PrimaryKey
        @ColumnInfo(name = "resultId")
        val eventId: String,

        @ColumnInfo(name = "votes")
        var votes: ArrayList<String>,

        @ColumnInfo(name = "lastModification")
        var lastModification: Long
)