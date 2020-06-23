package com.sela.youtubesearch.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 *  Data and Time Extension
 */
private val dateFormatter = SimpleDateFormat("dd/MM/yyyy\nHH:mm", Locale.US)
private val timeFormatter = SimpleDateFormat("HH:mm", Locale.US)

fun Long.getDateFormat(): String {
    val date = Date(this)
    return when {
        DateUtils.isToday(this) -> timeFormatter.format(date)
        else -> dateFormatter.format(date)
    }
}

fun Long.getTimeFormat():String{
    val hour = TimeUnit.SECONDS.toHours(this)
    val minutes = TimeUnit.SECONDS.toMinutes(this) % TimeUnit.HOURS.toMinutes(1)
    val seconds = TimeUnit.SECONDS.toSeconds(this) % TimeUnit.MINUTES.toSeconds(1)
    return String.format("%02d:%02d:%02d", hour,minutes,seconds)
}


