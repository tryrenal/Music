package com.redveloper.core_ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.redveloper.core_ui.R as CoreR

class RedveloperAppBar: BaseCustomView {

    private lateinit var btnBack: ImageView
    private lateinit var tvTitle: TextView

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){
        setupLayout()
        readAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        setupLayout()
        readAttributes(attrs)
    }

    private fun setupLayout(){
        val view = LayoutInflater.from(context).inflate(CoreR.layout.redveloper_appbar, this, false)
        addView(view)

        btnBack = findViewById<ImageView>(CoreR.id.btn_appbar_back)
        tvTitle = findViewById<TextView>(CoreR.id.tv_appbar_title)
    }

    private fun readAttributes(attrs: AttributeSet?){
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, CoreR.styleable.RedveloperAppBar)
            setAppbarText(typedArray.getString(CoreR.styleable.RedveloperAppBar_label) ?: "")
            typedArray.recycle()
        }
    }

    fun setAppbarText(text: String){
        tvTitle.text = text
    }

    fun onBack(unit: () -> Unit){
        this.btnBack.setOnClickListener {
            unit.invoke()
        }
    }
}