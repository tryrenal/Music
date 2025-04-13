package com.redveloper.music.data.mapper

import com.redveloper.music.data.model.MusicResponse
import com.redveloper.music.domain.model.Music
import com.redveloper.music.domain.util.convertToDate
import java.util.Date

fun MusicResponse.toMusic(): Music {
    return Music(
        artisId = artistId ?: -1,
        artisName = artistName ?: "",
        collectionName = collectionName ?: "",
        coverArt = artworkUrl100 ?: "",
        releaseDate = if (!releaseDate.isNullOrBlank()) convertToDate(releaseDate) else Date(),
        music = previewUrl ?: ""
    )
}