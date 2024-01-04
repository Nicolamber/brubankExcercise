package com.nlambertucci.brubank.domain.usecase

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoritesMoviesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {

    fun getFavorites(): List<Movie>? {
        return repository.getUserFavorites()
    }
}