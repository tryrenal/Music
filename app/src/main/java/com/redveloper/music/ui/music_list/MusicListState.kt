package com.redveloper.music.ui.music_list

import com.redveloper.music.domain.model.Music

data class MusicListState(
    val isLoading: Boolean = false,
    val songs: List<Music> = emptyList(),
    val error: String? = null
)
