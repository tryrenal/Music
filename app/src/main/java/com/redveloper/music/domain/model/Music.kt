package com.redveloper.music.domain.model

import java.util.Date

data class Music(
    val artisId: Int,
    val artisName: String,
    val collectionName: String,
    val coverArt: String,
    val releaseDate: Date,
    val music: String
)