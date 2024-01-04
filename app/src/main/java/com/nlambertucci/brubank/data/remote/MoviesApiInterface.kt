package com.nlambertucci.brubank.data.remote


import com.nlambertucci.brubank.BuildConfig
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.domain.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApiInterface {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int = 1
    ): Response<MoviesResponse>

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): Response<MoviesResponse>
}