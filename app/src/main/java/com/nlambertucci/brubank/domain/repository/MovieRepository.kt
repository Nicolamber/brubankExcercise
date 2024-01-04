package com.nlambertucci.brubank.domain.repository

import com.nlambertucci.brubank.domain.model.Genre
import com.nlambertucci.brubank.domain.model.MoviesResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(): Response<MoviesResponse>
    suspend fun getMovieByName(name: String): Response<MoviesResponse>

    suspend fun getGenres(): Response<Genre>

}