package com.nlambertucci.brubank.common

object Constants {
    // Url base path
    const val BASE_URL = "https://api.themoviedb.org/3/"

    // Image base path
    const val IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500"

    //timeout handler
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    const val CONNECT_TIMEOUT = 20L

    // Default language
    const val LANGUAGE = "en-US"

    // Shared pref.
    const val SHARED_PREF_KEY = "favorites_shared_preferences"
    const val MOVIE_LIST_KEY = "saved_favs_movies"

    //Navigation extras
    const val MOVIE_DETAIL = "movie_detail_info"
}