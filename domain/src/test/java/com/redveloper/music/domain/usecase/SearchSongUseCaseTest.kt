package com.redveloper.music.domain.usecase

import com.redveloper.music.domain.model.Music
import com.redveloper.music.domain.repository.MusicRepository
import com.redveloper.music.domain.util.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import java.util.Date

class SearchSongUseCaseTest {

    @Mock
    private lateinit var mockRepository: MusicRepository
    private lateinit var useCase: SearchSongUseCase

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        useCase = SearchSongUseCase(mockRepository)
    }

    @Test
    fun `search with valid query return data`() = runTest {
        val dataTest = listOf<Music>(
            Music(
                artisId = 1,
                artisName = "Song 1",
                collectionName = "coldplay",
                coverArt = "coldplay artwork",
                releaseDate = Date(),
                music = "coldplay.mp3")
        )
        `when`(mockRepository.searchMusic(eq("Song 1"))).thenReturn(dataTest)

        val result = useCase.invoke(flowOf("Song 1")).toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(dataTest, (result[1] as Resource.Success).data)
    }

    @Test
    fun  `query shorter less than 3 will be ignored`() = runTest {
        val result = useCase.invoke(flowOf("re")).toList()
        assertTrue(result.isEmpty())
    }



}