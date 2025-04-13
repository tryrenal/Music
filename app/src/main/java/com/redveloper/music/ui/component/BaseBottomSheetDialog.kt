package com.redveloper.music.ui.component

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.redveloper.music.R

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource(), container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            createBottomSheet(it as BottomSheetDialog)
        }
        return dialog
    }

    open fun createBottomSheet(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            ViewCompat.setBackground(bottomSheet, ContextCompat.getDrawable(requireContext(), R.drawable.bg_bottom_sheet))
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.run {
            window?.run {
                attributes.windowAnimations = R.style.BottomSheetAnimation
            }
        }
    }

    fun safeShow(supportFragmentManager: FragmentManager, tag: String?) {
        if (!isAdded) {
            show(supportFragmentManager, tag)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isAdded && !manager.isDestroyed) {
            super.show(manager, tag)
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetStyle
    }

    abstract fun layoutResource(): Int
}