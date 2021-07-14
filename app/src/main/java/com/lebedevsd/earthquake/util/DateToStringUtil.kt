package com.lebedevsd.earthquake.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateToStringUtil {
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    fun dateToString(date: Date) = dateFormat.format(date)
}
