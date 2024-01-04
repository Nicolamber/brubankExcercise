package com.nlambertucci.brubank.domain.usecase

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import javax.inject.Inject

class SaveMovieAsFavoriteUseCase  @Inject constructor(
    private val repository: FavoritesRepository
) {
    fun saveMovieAsFavorite(movie: Movie){
        repository.saveUserFavorites(movie)
    }
}