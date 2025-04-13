package com.redveloper.music.data.di

import com.redveloper.music.data.repository.MusicRepositoryImpl
import com.redveloper.music.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository
}