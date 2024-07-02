package com.example.footyfaces.framework.di

import android.content.Context
import com.example.footyfaces.BuildConfig
import com.example.footyfaces.data.NetworkConstants
import com.example.footyfaces.data.remote.ApiService
import com.example.footyfaces.data.repository.PlayerRepositoryImpl
import com.example.footyfaces.domain.repository.PlayerRepository
import com.example.footyfaces.domain.usecase.GetPlayers
import com.example.footyfaces.domain.usecase.GetPlayersImpl
import com.example.footyfaces.framework.connectivity.ConnectivityMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add("api.sportmonks.com", "sha256/GHI9ahaGhb8D93oYKqYns185/j2vRP91WgSKO8+1nrA=")
            .build()

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    @Named("onlineInterceptor")
    fun provideOnlineInterceptor(): Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    @Named("apiKeyInterceptor")
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_token", BuildConfig.PLAYERS_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        chain.proceed(request)
    }


    @Provides
    @Singleton
    fun provideIkHttpClient(
        cache: Cache,
        certificatePinner: CertificatePinner,
        @Named("onlineInterceptor") onlineInterceptor: Interceptor,
        @Named("apiKeyInterceptor") apiKeyInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .certificatePinner(certificatePinner)
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePlayerRepository(apiService: ApiService): PlayerRepository {
        return PlayerRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetPlayers(playerRepository: PlayerRepository): GetPlayers {
        return GetPlayersImpl(playerRepository)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)

}