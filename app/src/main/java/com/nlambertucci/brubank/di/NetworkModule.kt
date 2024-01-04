package com.nlambertucci.brubank.di

import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.data.remote.MoviesApiInterface
import com.nlambertucci.brubank.data.repository.MovieRepositoryImpl
import com.nlambertucci.brubank.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun getMoviesApiInstance(): MoviesApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClientInstance())
            .build()
            .create(MoviesApiInterface::class.java)
    }

    @Provides
    fun getOkHttpClientInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}