package com.pwr.knif.omatko


class ScheduleEvent (
        val id:String,
        val title:String,
        val presenter:String,
        val shortDescription:String,
        val longDescription:String,
        val beginTime:List<Int>,
        val endTime:List<Int>,
        var isChecked:Boolean = false,
        var showLongDescription:Boolean = false,
        var eventCalendarID:Long? = null)