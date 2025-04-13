package com.redveloper.music.util

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.redveloper.music.R

fun TextView.textIsActive(context: Context, active: Boolean){
    if(active)
        this.setTextColor(ContextCompat.getColor(context, R.color.green))
    else
        this.setTextColor(ContextCompat.getColor(context, R.color.white))
}

fun getGroupName(name: String): String{
    return "Song-$name"
}

fun View.isVisible(show: Boolean){
    if(show)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}