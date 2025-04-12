package com.redveloper.music.data.repository

import com.redveloper.music.data.ApiService
import com.redveloper.music.data.mapper.toMusic
import com.redveloper.music.domain.model.Music
import com.redveloper.music.domain.repository.MusicRepository
import com.redveloper.music.util.Constant
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MusicRepository {
    override suspend fun searchMusic(song: String): List<Music> {
        val response = api.searchMusic(
            entity = Constant.ENTITY_TYPE,
            limit = Constant.LIMIT_MUSIC,
            term = song
        )
        return  response.results.map { it.toMusic() }
    }
}