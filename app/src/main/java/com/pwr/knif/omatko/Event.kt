package com.pwr.knif.omatko

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "events")
data class Event(

        @PrimaryKey
        @ColumnInfo(name = "eventId")
        val eventId: String,

        @ColumnInfo(name = "title")
        var title: String,

        @ColumnInfo(name = "presenter")
        val presenter: String,

        @ColumnInfo(name = "place")
        val place: String,

        @ColumnInfo(name = "shortDescription")
        val shortDescription: String,

        @ColumnInfo(name = "longDescription")
        val longDescription: String,

        @ColumnInfo(name = "beginTime")
        val beginTime: Long,

        @ColumnInfo(name = "endTime")
        val endTime: Long,

        @ColumnInfo(name = "type")
        val type: String,

        @ColumnInfo(name = "day")
        val day: String,

        @ColumnInfo(name = "lastModification")
        var lastModification: Long,

        @ColumnInfo(name = "isChecked")
        var isChecked: Boolean = false,

        @ColumnInfo(name = "showLongDescription")
        var showLongDescription: Boolean = false,

        @ColumnInfo(name = "eventCalendarID")
        var eventCalendarID: Long? = null


)