package com.redveloper.core_ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.redveloper.core_ui.utils.view.dpToPx
import com.redveloper.core_ui.utils.view.isVisbile
import com.redveloper.core_ui.R as CoreR

class RedveloperButton: BaseCustomView{

    private lateinit var icButton: ImageView
    private lateinit var btn: ConstraintLayout
    private lateinit var textBtn: TextView

    private lateinit var variant: RedveloperButtonVariant

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
        val view = LayoutInflater.from(context).inflate(CoreR.layout.redveloper_button, this, false)
        addView(view)

        icButton = findViewById<ImageView>(CoreR.id.ic_btn)
        btn = findViewById<ConstraintLayout>(CoreR.id.btn)
        textBtn = findViewById<TextView>(CoreR.id.txt_btn)
    }

    private fun readAttributes(attrs: AttributeSet?){
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, CoreR.styleable.RedveloperButton)
            val variantStyle = typedArray.getInt(CoreR.styleable.RedveloperButton_btnStyle,
                RedveloperButtonVariant.PRIMARY.ordinal)
            variant = RedveloperButtonVariant.getNameById(variantStyle) ?: RedveloperButtonVariant.PRIMARY

            val label = typedArray.getString(CoreR.styleable.RedveloperButton_label)
            val icon = typedArray.getDrawable(CoreR.styleable.RedveloperButton_icon)
            val iconColor = typedArray.getResourceId(CoreR.styleable.RedveloperButton_iconColor, -1)

            setVariant(variant)
            setIcon(icon)
            setIconColor(iconColor)
            setText(label?:"")

            typedArray.recycle()
        }
    }

    fun onClick(unit: () -> Unit){
        this.btn.setOnClickListener {
            unit.invoke()
        }
    }

    fun setIconColor(@ColorRes color: Int){
        if(color != -1 && color != 0){
            this.icButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, color))
        }
    }

    fun setIcon(icon: Drawable?){
        icButton.isVisbile(icon != null)
        icon?.let {
            Glide.with(context)
                .load(it)
                .into(icButton)
        }
    }

    fun setText(text: String){
        this.textBtn.text = text
    }

    fun setVariant(variant: RedveloperButtonVariant){
        if(variant == RedveloperButtonVariant.PRIMARY){
            setPrimaryButton()
        }
        else if(variant == RedveloperButtonVariant.SECONDARY){
            setSecondaryButton()
        }
    }

    private fun setPrimaryButton(){
        this.textBtn.setTextColor(ContextCompat.getColor(context, CoreR.color.black))
        this.btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, CoreR.color.lightGreen))
    }

    private fun setSecondaryButton(){
        this.textBtn.setTextColor(ContextCompat.getColor(context, CoreR.color.white))
        this.btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, CoreR.color.black))
        val background = this.btn.background
        if (background is GradientDrawable){
            val mutableDrawable = background.mutate() as GradientDrawable
            val strokeWidth = 1.dpToPx(context)
            mutableDrawable.setStroke(
                strokeWidth,
                ContextCompat.getColor(context, CoreR.color.white)
            )
            btn.background = mutableDrawable
            btn.backgroundTintList = null
        }
    }

    fun isEnable(enable: Boolean){
        if (enable)
            enableButton()
        else
            disableButton()
    }

    private fun enableButton(){
        this.btn.isEnabled = true
        this.btn.isClickable = true

        setVariant(variant)
    }

    private fun disableButton(){
        this.btn.isEnabled = false
        this.btn.isClickable = false

        this.btn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, CoreR.color.darkGrey))
        this.textBtn.setTextColor(ContextCompat.getColor(context, CoreR.color.black))
    }

    enum class RedveloperButtonVariant(val id: Int){
        PRIMARY(0),
        SECONDARY(1);


        companion object{
            fun getNameById(id: Int): RedveloperButtonVariant? {
                return enumValues<RedveloperButtonVariant>().find { it.id == id }
            }
        }

    }


}