package com.example.moviecatalog.domain.usecase

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateConverter(){
    private val uiDateFormat = SimpleDateFormat(
        "d MMMM yyyy", Locale("ru","RU"))
    private  val isoDateFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US);

    fun convertSelectedToUI(date:android.icu.util.Calendar):String{
        return uiDateFormat.format(date.time)
    }

    fun convertToIso(date:String):String{
       val parsedDate = uiDateFormat.parse(date)
        return isoDateFormat.format(parsedDate as Date)
    }

}