package com.redveloper.music.di

import com.redveloper.music.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }
}