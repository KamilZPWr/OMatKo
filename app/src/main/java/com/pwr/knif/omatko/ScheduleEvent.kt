package com.pwr.knif.omatko

import java.sql.Time
import java.util.*

/**
 * Created by Kamil on 27.11.2017.
 */
data class ScheduleEvent (
        val id:String,
        val title:String,
        val presenter:String,
        val description:String,
        var ticked:Boolean)