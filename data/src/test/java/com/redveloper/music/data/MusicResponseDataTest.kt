package com.redveloper.music.data

import com.redveloper.music.data.model.MusicResponse

fun createMusicResponseDataTest(
    artistId: Int,
    artisName: String,
    previewUrl: String
): MusicResponse{
    return MusicResponse(
        artistId = artistId,
        artistName = artisName,
        previewUrl = previewUrl,

        artistViewUrl = null,
        artworkUrl30 = null,
        artworkUrl60 = null,
        artworkUrl100 = null,
        collectionExplicitness = null,
        collectionId = null,
        collectionName = null,
        collectionPrice = null,
        collectionViewUrl = null,
        country = null,
        currency = null,
        discCount = null,
        discNumber = null,
        isStreamable = null,
        kind = null,
        primaryGenreName = null,
        releaseDate = null,
        trackCensoredName = null,
        trackCount = null,
        trackExplicitness = null,
        trackId = null,
        trackName = null,
        trackNumber = null,
        trackPrice = null,
        trackTimeMillis = null,
        trackViewUrl = null,
        wrapperType = null,
        collectionCensoredName = null
    )
}