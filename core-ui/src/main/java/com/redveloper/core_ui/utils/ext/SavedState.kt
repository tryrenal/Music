package com.redveloper.core_ui.utils.ext

import android.os.Parcel
import android.os.Parcelable
import android.util.SparseArray
import android.view.View

internal class SavedState:View.BaseSavedState {

    var childrenStates: SparseArray<Parcelable>? = null

    constructor(superState: Parcelable?) : super(superState)

    constructor(source: Parcel) : super(source) {
        childrenStates = source.readSparseArray<Parcelable>(javaClass.classLoader)
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSparseArray(childrenStates as SparseArray<Any>)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(source: Parcel) = SavedState(source)
            override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
        }
    }
}