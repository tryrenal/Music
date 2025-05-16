package com.redveloper.feature_search.ui.music_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redveloper.music.domain.usecase.SearchSongUseCase
import com.redveloper.music.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val searchSongUseCase: SearchSongUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _musicState = MutableStateFlow<MusicListState>(MusicListState())
    val musicState: StateFlow<MusicListState> = _musicState.asStateFlow()

    fun clearSearch(){
        viewModelScope.launch {
            _searchQuery.value = ""
            _musicState.update { it.copy(isLoading = false, songs = emptyList(), error = null) }
        }
    }

    fun searchSong(query: String){
        viewModelScope.launch {
            _searchQuery.value = query
            searchSongUseCase.invoke(_searchQuery)
                .collect { result ->
                    when(result){
                        is Resource.Success -> {
                            _musicState.update { it.copy(
                                isLoading = false,
                                songs = result.data ?: emptyList(),
                                error = null
                            ) }
                        }
                        is Resource.Error -> {
                            _musicState.update { it.copy(
                                isLoading = false,
                                songs = emptyList(),
                                error = result.message
                            ) }
                        }
                        is Resource.Loading -> {
                            _musicState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }
}