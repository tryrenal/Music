package com.redveloper.core_ui.utils.view

import android.view.View

fun View.isVisbile(visible: Boolean){
    if (visible)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}