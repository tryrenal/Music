package com.redveloper.core_ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.redveloper.core_ui.R as CoreR

class RedveloperChecked : BaseCustomView {

    private lateinit var tvTitle: TextView
    private lateinit var imgCheck: ImageView
    private var isChecked: Boolean = false

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs){
        setupLayout()
        readAttribute(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        setupLayout()
        readAttribute(attrs)
    }

    private fun setupLayout(){
        val view = LayoutInflater.from(context).inflate(CoreR.layout.redveloper_checked, this, false)
        addView(view)

        tvTitle = findViewById<TextView>(CoreR.id.tv_title)
        imgCheck = findViewById<ImageView>(CoreR.id.img_check)
    }

    private fun readAttribute(attrs: AttributeSet?){
        attrs?.let{
            val typedArray = context.obtainStyledAttributes(attrs, CoreR.styleable.RedveloperChecked)
            setLabel(typedArray.getString(CoreR.styleable.RedveloperChecked_label) ?: "")
            setColorLabel(typedArray.getResourceId(CoreR.styleable.RedveloperChecked_labelColor, -1))
            typedArray.recycle()
        }
    }

    fun setLabel(text: String){
        tvTitle.text = text
    }

    fun setColorLabel(@ColorRes color: Int){
        if (color != -1 && color != 0){
            tvTitle.setTextColor(ContextCompat.getColor(context, color))
        }
    }

    fun onChecked(unit: (Boolean) -> Unit){
        imgCheck.setOnClickListener {
            unit.invoke(!isChecked)
            this.isChecked = !this.isChecked
            checkedStyle(this.isChecked)
        }
    }

    fun setChecked(checked: Boolean){
        this.isChecked = checked
        checkedStyle(this.isChecked)
    }

    private fun checkedStyle(checked: Boolean){
        if(checked){
            Glide.with(context)
                .load(CoreR.drawable.ic_checked)
                .into(imgCheck)
            imgCheck.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, CoreR.color.green))
        }
        else {
            Glide.with(context)
                .load(CoreR.drawable.ic_unchecked)
                .into(imgCheck)
            imgCheck.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, CoreR.color.white))
        }
    }
}