package com.nlambertucci.brubank.domain.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("page")
    val currentPage: Int,
    @SerializedName("results")
    val movies: List<Movie>?,
    @SerializedName("total_pages")
    val pages: Int
)