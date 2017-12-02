package com.pwr.knif.omatko

/**
 * Created by Kamil on 27.11.2017.
 */
class ScheduleEvent (
        val id:String,
        val title:String,
        val presenter:String,
        val shortDescription:String,
        val longDescription:String,
        var isTicked:Boolean = false,
        var showLongDescription:Boolean = false)