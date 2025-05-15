package com.redveloper.core_ui.components.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.redveloper.core_ui.utils.view.gone
import com.redveloper.core_ui.utils.view.visible
import com.redveloper.core_ui.R as CoreR

class RedveloperSpinnerAdapter(private val context: Context): BaseAdapter() {

    var items: List<RedveloperSpinnerModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: ((RedveloperSpinnerModel) -> Unit)? = null
    var selectedKey: String? = null

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any? {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view: View? = convertView
        if(convertView == null)
            view = LayoutInflater.from(context).inflate(CoreR.layout.redveloper_spinner_item, null)

        val item = getItem(position) as RedveloperSpinnerModel
        view?.setOnClickListener {
            clickListener?.invoke(item)
        }

        val tvItem = view?.findViewById<TextView>(CoreR.id.tv_item)
        val ivSelected = view?.findViewById<ImageView>(CoreR.id.iv_dropdown)

        tvItem?.text = item.value
        ivSelected?.gone()

        if (selectedKey == item.key)
            ivSelected?.visible()

        return view
    }


}