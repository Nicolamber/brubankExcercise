package com.nlambertucci.brubank.domain.model

data class SearchDto(
    val movies: List<Movie>,
    val favoritesMovies: List<Movie>?
)
