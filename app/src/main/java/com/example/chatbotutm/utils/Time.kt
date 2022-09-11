package com.example.chatbotutm.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Time {

    fun timeStamp():String{
        val timeStaamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStaamp.time))
        return  time.toString()
    }
}