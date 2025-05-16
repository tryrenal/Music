package com.redveloper.core_ui.utils.ext

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.redveloper.core_ui.R

fun TextView.textIsActive(context: Context, active: Boolean){
    if(active)
        this.setTextColor(ContextCompat.getColor(context, R.color.green))
    else
        this.setTextColor(ContextCompat.getColor(context, R.color.white))
}


fun View.isVisible(show: Boolean){
    if(show)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}