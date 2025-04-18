package com.example.moviecatalog.domain.usecase

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DateConverterUseCase() {
    private val uiDateFormat = SimpleDateFormat(
        "d MMMM yyyy", Locale("ru", "RU")
    )
    private val isoDateFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US
    )

    private val isoDateFormatReview = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.US
    )

    fun convertSelectedToUI(date: android.icu.util.Calendar): String {
        return uiDateFormat.format(date.time)
    }

    fun convertToIso(date: String): String {
        val parsedDate = uiDateFormat.parse(date)
        return isoDateFormat.format(parsedDate as Date)
    }

    fun convertRemoteToUI(date: String): String {
        val parsedDate = isoDateFormat.parse(date)
        return uiDateFormat.format(parsedDate as Date)
    }

    fun getCurrentTime(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH")
        return currentTime.format(formatter)
    }

    fun convertReviewDate(date: String): String {
        val parsedDate = isoDateFormatReview.parse(date)
        return uiDateFormat.format(parsedDate as Date)
    }

}