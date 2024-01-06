package com.nlambertucci.brubank.domain.model

import androidx.annotation.Keep

@Keep
data class SearchDto(
    val movies: List<Movie>,
    val favoritesMovies: List<Movie>?
)
