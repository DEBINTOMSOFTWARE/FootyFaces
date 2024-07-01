package com.example.footyfaces.framework.di

import com.example.footyfaces.data.repository.PlayerRepositoryImpl
import com.example.footyfaces.domain.repository.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(playerRepositoryImpl: PlayerRepositoryImpl): PlayerRepository

}