package com.nlambertucci.brubank.data.repository

import com.nlambertucci.brubank.BuildConfig
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.data.remote.MoviesApiInterface
import com.nlambertucci.brubank.domain.model.MoviesResponse
import com.nlambertucci.brubank.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MoviesApiInterface
) : MovieRepository {
    override suspend fun getMovies(page: Int): Response<MoviesResponse> {
        return api.getMovies(
            BuildConfig.API_KEY,
            Constants.LANGUAGE,
            page
        )
    }

    override suspend fun getMovieByName(name: String): Response<MoviesResponse> {
        return api.getMovieByName(query = name)
    }

}