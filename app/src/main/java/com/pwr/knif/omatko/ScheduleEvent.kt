package com.pwr.knif.omatko


class ScheduleEvent (
        val id:String,
        val title:String,
        val presenter:String,
        val shortDescription:String,
        val longDescription:String,
        val beginTime: Long,
        val endTime: Long,
        var isChecked:Boolean = false,
        var showLongDescription:Boolean = false,
        var eventCalendarID:Long? = null)