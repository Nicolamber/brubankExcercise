package com.nlambertucci.brubank.presentation.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nlambertucci.brubank.domain.model.*
import com.nlambertucci.brubank.domain.repository.MovieRepository
import com.nlambertucci.brubank.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase,
    private val saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase,
    private val deleteMovieFromFavoritesUseCase: DeleteMovieFromFavoritesUseCase

) : ViewModel() {
    sealed class MovieStatus {
        object Loading : MovieStatus()
        data class Success(val favorites: List<Movie>?) : MovieStatus()
        data class Error(val message: String?) : MovieStatus()
        data class SearchSuccess(val searchResult: SearchDto) : MovieStatus()
    }

    private val status = MutableLiveData<MovieStatus>()
    val moviesStatus: LiveData<MovieStatus> = status
    private var favoritesMovies: List<Movie>? = null

    val moviesList = Pager(PagingConfig(pageSize = 20)) {
        GetMoviesPaginatedUseCase(repository)
    }.flow.cachedIn(viewModelScope)

    init {
        favoritesMovies = getFavoritesMoviesUseCase.getFavorites()
    }

    fun initView() {
        fetchFavoritesMovies()
    }

    private fun fetchFavoritesMovies() {
        status.value = MovieStatus.Loading
        status.value = MovieStatus.Success(favoritesMovies)
    }

    fun searchMovie(query: String) {
        getMovieByQuery(query)
    }

    private fun getMovieByQuery(query: String) {
        status.value = MovieStatus.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getMovieByName(query)
            }.onSuccess { response ->
                response.body()?.movies?.let {
                    status.value = MovieStatus.SearchSuccess(
                        SearchDto(it, favoritesMovies)
                    )
                }
                return@launch
            }.onFailure {
                status.value = MovieStatus.Error(it.message)
            }
        }
    }

    fun setAsFavorite(movie: Movie) {
        saveMovieAsFavorite(movie)
    }

    private fun saveMovieAsFavorite(movie: Movie) {
        saveMovieAsFavoriteUseCase.saveMovieAsFavorite(movie)
    }

    fun removeMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavorites(movie)
    }

    private fun deleteMovieFromFavorites(movie: Movie) {
        deleteMovieFromFavoritesUseCase.deleteMovie(movie)
    }

}
