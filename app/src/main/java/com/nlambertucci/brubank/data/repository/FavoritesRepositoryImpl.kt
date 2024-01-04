package com.nlambertucci.brubank.data.repository

import android.content.SharedPreferences
import com.nlambertucci.brubank.common.Constants
import com.nlambertucci.brubank.common.toJson
import com.nlambertucci.brubank.common.toMovie
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : FavoritesRepository {


    override fun getUserFavorites(): MutableList<Movie>? {
        val favMovies = sharedPreferences.getStringSet(
            Constants.MOVIE_LIST_KEY,
            setOf()
        ) ?: return null

        return favMovies.map {
            it.toMovie()
        }.toMutableList()
    }


    override fun saveUserFavorites(movie: Movie) {
        val editor = sharedPreferences.edit()
        val favMovies = getUserFavorites().orEmpty().toMutableSet()
        favMovies.add(movie)
        editor.putStringSet(
            Constants.MOVIE_LIST_KEY,
            favMovies.map { it.toJson() }.toSet()
        )
        editor.apply()
    }

    override fun deleteMovieFromFavorites(movie: Movie) {
        val editor = sharedPreferences.edit()
        val moviesList = getUserFavorites()
        moviesList?.remove(movie)
        editor.putStringSet(
            Constants.MOVIE_LIST_KEY,
            moviesList?.map { it.toJson() }?.toSet()
        )
        editor.apply()
    }
}