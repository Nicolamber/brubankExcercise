package com.nlambertucci.brubank.domain.model

data class MovieListDto(
    val movies: List<Movie>,
    val favorites: List<Movie>?
)
