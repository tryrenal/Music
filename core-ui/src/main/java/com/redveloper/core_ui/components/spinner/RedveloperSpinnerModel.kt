package com.redveloper.core_ui.components.spinner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedveloperSpinnerModel(
    val key: String,
    val value: String
): Parcelable