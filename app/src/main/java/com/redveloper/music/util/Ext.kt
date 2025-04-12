package com.redveloper.music.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertToDate(input: String): Date{
    val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    return simpleDateFormat.parse(input)
}