package com.nlambertucci.brubank.domain.usecase

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import javax.inject.Inject

open class DeleteMovieFromFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {

    fun deleteMovie(movie: Movie) {
        repository.deleteMovieFromFavorites(movie)
    }
}