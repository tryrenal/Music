package com.redveloper.music.domain.usecase

import com.redveloper.music.domain.model.Music
import com.redveloper.music.domain.repository.MusicRepository
import com.redveloper.music.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchSongUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    operator fun invoke(query: Flow<String>): Flow<Resource<List<Music>>>{
        var result = query
            .debounce(400)
            .filter { it.length > 3 }
            .flatMapLatest { query ->
                if (query.isNotBlank()){
                    searchSong(query)
                }
                else {
                    flow { emit(Resource.Success(emptyList())) }
                }
            }
            .catch { error ->
                emit(Resource.Error(error.localizedMessage ?: "Unknown error", null))
            }
        return result
    }

    private fun searchSong(query: String): Flow<Resource<List<Music>>>{
        return flow {
            emit(Resource.Loading(true))
            try{
                val response = musicRepository.searchMusic(query)
                emit(Resource.Success(response))
            }
            catch (e: IOException){
                emit(Resource.Error("you dont have internet: ${e.localizedMessage}"))
            }
            catch (e: HttpException){
                emit(Resource.Error("Server error: ${e.localizedMessage}"))
            }
            catch (e: Exception){
                emit(Resource.Error("error: ${e.localizedMessage}"))
            }
            finally {
                emit(Resource.Loading(false))
            }
        }
    }
}