package com.redveloper.music.data.model


import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("resultCount")
    val resultCount: Int?,
    @SerializedName("results")
    val results: List<MusicResponse>?
)