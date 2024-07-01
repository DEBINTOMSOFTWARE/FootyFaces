package com.example.footyfaces.framework.di

import android.util.Log
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.domain.usecase.GetPlayersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    init {
        Log.d("Hilt", "UseCaseModule created")
    }

    @Binds
    @Singleton
    abstract fun bindGetPlayers(getPlayersImpl: GetPlayersImpl): GetPlayers

}