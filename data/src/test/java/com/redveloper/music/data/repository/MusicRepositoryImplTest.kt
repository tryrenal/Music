package com.redveloper.music.data.repository

import com.redveloper.music.data.ApiService
import com.redveloper.music.data.createMusicResponseDataTest
import com.redveloper.music.data.model.ResultResponse
import com.redveloper.music.data.util.Constant
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MusicRepositoryImplTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var repository: MusicRepositoryImpl

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repository = MusicRepositoryImpl(mockApiService)
    }

    @Test
    fun `searchMusic returns mapped music list on success`() = runTest {
        // Arrange
        val testQuery = "Adele"
        val mockResponse = ResultResponse(
            resultCount = 2,
            results = listOf(
                createMusicResponseDataTest(
                    artistId = 1,
                    artisName = "Adele",
                    previewUrl = "100.mp3"
                ),
                createMusicResponseDataTest(
                    artistId = 2,
                    artisName = "Coldplay",
                    previewUrl = "102.mp3"
                )
            )
        )

        `when`(mockApiService.searchMusic(
            entity = Constant.ENTITY_TYPE,
            limit = Constant.LIMIT_MUSIC,
            term = testQuery
        )).thenReturn(mockResponse)

        // Act
        val result = repository.searchMusic(testQuery)

        // Assert
        assertEquals(2, result.size)
        assertEquals("Adele", result[0].artisName)
        assertEquals("102.mp3", result[1].music)
    }

    @Test
    fun `searchMusic returns empty list when no results`() = runTest {
        // Arrange
        val testQuery = "unknownartist"
        val mockResponse = ResultResponse(
            resultCount = 0,
            results = emptyList()
        )

        `when`(mockApiService.searchMusic(
            entity = Constant.ENTITY_TYPE,
            limit = Constant.LIMIT_MUSIC,
            term = testQuery
        )).thenReturn(mockResponse)

        // Act
        val result = repository.searchMusic(testQuery)

        // Assert
        assertTrue(result.isEmpty())
    }

}