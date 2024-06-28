package com.example.footyfaces.data.remote

import com.example.footyfaces.data.NetworkConstants
import com.example.footyfaces.data.model.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("${NetworkConstants.VERSION}${NetworkConstants.PATH}${NetworkConstants.PLAYERS}")
    suspend fun getPlayers(@Query("page") page: Int): PlayerResponse
}