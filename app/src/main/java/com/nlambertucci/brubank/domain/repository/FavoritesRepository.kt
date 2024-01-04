package com.nlambertucci.brubank.domain.repository

import com.nlambertucci.brubank.domain.model.Movie

interface FavoritesRepository {
    fun getUserFavorites(): MutableList<Movie>?
    fun saveUserFavorites(movie: Movie)

    fun deleteMovieFromFavorites(movie: Movie)
}