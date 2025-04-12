package com.redveloper.music.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.redveloper.music.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun convertToDate(input: String): Date{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return simpleDateFormat.parse(input)
}

fun TextView.textIsActive(context: Context, active: Boolean){
    if(active)
        this.setTextColor(ContextCompat.getColor(context, R.color.green))
    else
        this.setTextColor(ContextCompat.getColor(context, R.color.white))
}

fun getGroupName(name: String): String{
    return "Song-$name"
}