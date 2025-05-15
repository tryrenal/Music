package com.redveloper.core_ui.components.spinner

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import com.redveloper.core_ui.components.BaseCustomView
import kotlinx.parcelize.Parcelize
import com.redveloper.core_ui.R as CoreR

class RedveloperSpinner: BaseCustomView {

    private lateinit var spinner: Spinner
    private lateinit var tvLabel: TextView
    private lateinit var containerSpinner: View
    private lateinit var tvSelectedItem: TextView

    private var selectedItem: RedveloperSpinnerModel? = null
    lateinit var spinnerAdapter: RedveloperSpinnerAdapter
    var selectedItemCallback: ((RedveloperSpinnerModel) -> Unit)? = null
    var clickedListener: (() -> Unit)? = null

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
        LayoutInflater.from(context).inflate(CoreR.layout.redveloper_spinner, this, true)

        spinner = findViewById<Spinner>(CoreR.id.spinner)
        tvLabel = findViewById<TextView>(CoreR.id.tv_label)
        containerSpinner = findViewById(CoreR.id.container_spinner)
        tvSelectedItem = findViewById<TextView>(CoreR.id.tv_selected_item_text)

        containerSpinner.setOnClickListener {
            spinner.performClick()
            clickedListener?.invoke()
        }

        setupAdapter()
    }

    override fun onSaveInstanceState(): Parcelable {
        val parentState = super.onSaveInstanceState()
        return SpinnerSaveState(parentState, spinnerAdapter.selectedKey, spinnerAdapter.items)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is SpinnerSaveState){
            spinnerAdapter.items = state.items
            state.selectedKey?.let {
                setSelectedKey(state.selectedKey)
            }
            return super.onRestoreInstanceState(state.saveState)
        }
    }

    private fun setupAdapter(){
        spinnerAdapter = RedveloperSpinnerAdapter(context)
        spinnerAdapter.clickListener = { selectedItem ->
            tvSelectedItem.text = selectedItem.value
            this.selectedItem = selectedItem
            selectedItemCallback?.invoke(selectedItem)

            if(spinner.adapter is RedveloperSpinnerAdapter){
                val customAdapter = (spinner.adapter as RedveloperSpinnerAdapter)
                customAdapter.selectedKey = selectedItem.key
                customAdapter.notifyDataSetChanged()
            }
        }

        spinner.adapter = spinnerAdapter
    }

    private fun readAttributes(attrs: AttributeSet?){
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, CoreR.styleable.RedveloperSpinner)
            setLabel(typedArray.getString(CoreR.styleable.RedveloperSpinner_label) ?: "")
            typedArray.recycle()
        }
    }

    fun setLabel(text: String){
        if (text.isNotBlank())
            tvLabel.text = text
    }

    fun setData(data: List<RedveloperSpinnerModel>){
        selectedItem = null
        spinnerAdapter.items = data
    }

    fun getData(): List<RedveloperSpinnerModel>{
        return spinnerAdapter.items
    }

    fun setSelectedKey(key: String){
        val adapter = spinner.adapter as RedveloperSpinnerAdapter
        adapter.selectedKey = key
        selectedItem = null
        tvSelectedItem.text = ""
        adapter.items.forEach {
            if(it.key == key){
                selectedItem = it
                tvSelectedItem.text = it.value
            }
        }
    }

    fun setSelectedKey(key: String, value: String){
        val adapter = spinner.adapter as RedveloperSpinnerAdapter
        adapter.selectedKey = key
        selectedItem = RedveloperSpinnerModel(key, value)
        tvSelectedItem.text = value
    }

    @Parcelize
    data class SpinnerSaveState(
        val saveState: Parcelable,
        val selectedKey: String?,
        val items: List<RedveloperSpinnerModel>
    ): Parcelable



}