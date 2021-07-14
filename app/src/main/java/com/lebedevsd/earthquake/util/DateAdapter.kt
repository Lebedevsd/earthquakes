package com.lebedevsd.earthquake.util

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson

import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateAdapter {

    //"2011-03-11 04:46:23"
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    @ToJson @Synchronized fun dateToJson(d: Date): String {
        return dateFormat.format(d)
    }

    @FromJson @Synchronized @Throws(ParseException::class) fun dateToJson(s: String): Date? {
        return dateFormat.parse(s)
    }
}
