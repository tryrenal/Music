package com.redveloper.core_ui.components.input

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.redveloper.core_ui.R as CoreR
import com.redveloper.core_ui.components.BaseCustomView
import com.redveloper.core_ui.utils.view.isVisbile

class RedveloperInput : BaseCustomView {

    private lateinit var tvLabel: TextView
    private lateinit var tvHint: TextView
    private lateinit var etInput: EditText

    constructor(context: Context): super(context)
    constructor(context: Context, attr: AttributeSet?): super(context, attr){
        setupLayout()
        readAttributes(attr)
    }
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int): super(context, attr, defStyleAttr){
        setupLayout()
        readAttributes(attr)
    }

    private fun setupLayout(){
        val view = LayoutInflater.from(context).inflate(CoreR.layout.redveloper_input, this, false)
        addView(view)

        tvLabel = findViewById<TextView>(CoreR.id.tv_label)
        tvHint = findViewById<TextView>(CoreR.id.tv_hint)
        etInput = findViewById<EditText>(CoreR.id.et_input)
    }

    private fun readAttributes(attrs: AttributeSet?){
        attrs?.let{
            val typedArray = context.obtainStyledAttributes(attrs, CoreR.styleable.RedveloperInput)
            val input = typedArray.getInt(CoreR.styleable.RedveloperInput_inputType, EnumInputType.TEXT.ordinal)
            setInputType(EnumInputType.values()[input])
            setHint(typedArray.getString(CoreR.styleable.RedveloperInput_hint) ?: "")
            setLabel(typedArray.getString(CoreR.styleable.RedveloperInput_label) ?: "")
            typedArray.recycle()
        }
    }

    fun setInputType(type: EnumInputType){
        when(type){
            EnumInputType.EMAIL -> {
                etInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            EnumInputType.TEXT -> {
                etInput.inputType = InputType.TYPE_CLASS_TEXT
            }
            EnumInputType.NUMBER -> {
                etInput.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    fun setHint(hint: String?){
        tvHint.isVisbile(!hint.isNullOrBlank())
        if (!hint.isNullOrBlank())
            tvHint.text = hint
    }

    fun setLabel(label: String?){
        tvLabel.isVisbile(!label.isNullOrBlank())
        if(!label.isNullOrBlank())
            tvLabel.text = label
    }

    var editTextContent: String?
        get() = etInput.text.toString()
        set(value) {
            etInput.setText(value)
        }

}