package com.redveloper.music.domain.repository

import com.redveloper.music.domain.model.Music

interface MusicRepository {
    suspend fun searchMusic(song: String): List<Music>
}