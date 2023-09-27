package com.mellowingfactory.wethm.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun getMonthList(): List<String>{
    val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)
    val list = arrayListOf<String>()
    Calendar.getInstance().let { calendar ->
        calendar.add(Calendar.MONTH, -11)
        for (i in 0 until 12) {
            list.add(formatter.format(calendar.timeInMillis))
            calendar.add(Calendar.MONTH, 1)
        }
    }
    return list
}