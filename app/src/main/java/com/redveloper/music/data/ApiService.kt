package com.redveloper.music.data

import com.redveloper.music.data.model.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchMusic(
        @Query("term") term: String,
        @Query("entity") entity: String,
        @Query("limit") limit: Int
    ): ResultResponse
}