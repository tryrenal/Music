package com.redveloper.core_ui.bottomsheet.basic

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.redveloper.core_ui.bottomsheet.BaseBottomSheet
import com.redveloper.core_ui.R as CoreR

class BasicBottomSheet: BaseBottomSheet() {
    private lateinit var tvMessage: TextView
    private lateinit var closeButton: View

    override fun layoutResource() = CoreR.layout.basic_bottomsheet_layout

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, CoreR.layout.basic_bottomsheet_layout, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvMessage = view.findViewById(CoreR.id.tv_message)
        closeButton = view.findViewById(CoreR.id.iv_close)

        arguments?.let {

            it.getString(KEY_MESSAGE)?.let { message->
                tvMessage.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }

            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }

    companion object{
        private const val KEY_MESSAGE = "key message"

        @JvmOverloads
        fun create(message:String): BasicBottomSheet{
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            val fragment = BasicBottomSheet()
            fragment.arguments = bundle

            return fragment
        }
    }
}