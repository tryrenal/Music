package com.redveloper.music.ui.component

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.redveloper.music.R

class ConfirmationBottomSheet : BaseBottomSheetFragment() {
    private lateinit var tvMessage: TextView
    private lateinit var closeButton: View

    override fun layoutResource() = R.layout.bottom_sheet_confirmation

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.bottom_sheet_confirmation, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvMessage = view.findViewById(R.id.tv_message)
        closeButton = view.findViewById(R.id.iv_close)

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
        fun create(message:String): ConfirmationBottomSheet{
            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            val fragment = ConfirmationBottomSheet()
            fragment.arguments = bundle

            return fragment
        }
    }
}