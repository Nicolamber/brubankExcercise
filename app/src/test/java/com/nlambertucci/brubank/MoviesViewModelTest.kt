package com.nlambertucci.brubank

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.model.MovieListDto
import com.nlambertucci.brubank.domain.model.MoviesResponse
import com.nlambertucci.brubank.domain.repository.MovieRepository
import com.nlambertucci.brubank.domain.usecase.DeleteMovieFromFavoritesUseCase
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import com.nlambertucci.brubank.domain.usecase.SaveMovieAsFavoriteUseCase
import com.nlambertucci.brubank.presentation.list.viewmodel.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.kotlin.given
import retrofit2.Response


@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase

    @Mock
    lateinit var saveMovieAsFavoriteUseCase: SaveMovieAsFavoriteUseCase

    @Mock
    lateinit var deleteMovieAsFavoriteUseCase: DeleteMovieFromFavoritesUseCase

    @Mock
    lateinit var observer: Observer<MoviesViewModel.MovieStatus>

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = MoviesViewModel(
            repository = movieRepository,
            getFavoritesMoviesUseCase = getFavoritesMoviesUseCase,
            saveMovieAsFavoriteUseCase = saveMovieAsFavoriteUseCase,
            deleteMovieFromFavoritesUseCase = deleteMovieAsFavoriteUseCase
        )

        viewModel.moviesLiveStatus.observeForever(observer)
    }

    @Test
    fun `fetchMovies should emit Success on successful data retrieval`() = runBlocking {

        Dispatchers.setMain(Dispatchers.Unconfined)

        val mockedResponse = Response.success(MoviesResponse(1, getMockedMoviesList(), 20))
        val favMovies = getMockedFavMoviesList()

        given(getFavoritesMoviesUseCase.getFavorites()).willReturn(favMovies)

        `when`(movieRepository.getMovies()).thenReturn(mockedResponse)

        val moviesDto = MovieListDto(mockedResponse.body()?.movies ?: listOf(), favMovies)

        viewModel.initView()

        verify(observer).onChanged(MoviesViewModel.MovieStatus.Loading)
        verify(observer).onChanged(MoviesViewModel.MovieStatus.Success(moviesDto))

        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMovies should emit error on error data retrieval`() = runBlocking {

        Dispatchers.setMain(Dispatchers.Unconfined)

        val errorMessage = "Something went wrong"
        val favMovies = getMockedFavMoviesList()

        given(getFavoritesMoviesUseCase.getFavorites()).willReturn(favMovies)

        `when`(movieRepository.getMovies()).thenThrow(NullPointerException(errorMessage))

        viewModel.initView()

        verify(observer).onChanged(MoviesViewModel.MovieStatus.Loading)
        verify(observer).onChanged(MoviesViewModel.MovieStatus.Error(errorMessage))

        Dispatchers.resetMain()
    }

    @Test
    fun `setAsFavorite should save movie as favorite`() {
        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )

        viewModel.setAsFavorite(movie)

        verify(saveMovieAsFavoriteUseCase).saveMovieAsFavorite(movie)
    }

    @Test
    fun `removeMovieFromFavorites should remove movie as favorite`() {
        val movie = Movie(
            "1", "Star wars I", "", "", "", ""
        )

        viewModel.removeMovieFromFavorites(movie)

        verify(deleteMovieAsFavoriteUseCase).deleteMovie(movie)
    }
}

private fun getMockedFavMoviesList(): List<Movie> {
    return listOf(
        Movie("1", "Avengers: End game", "", "", "", ""),
        Movie("2", "Harry Potter", "", "", "", ""),
        Movie("3", "Back to the Future", "", "", "", ""),
        Movie("4", "Terminator", "", "", "", ""),
    )
}

private fun getMockedMoviesList(): List<Movie> {
    return listOf(
        Movie("1", "Star wars I", "", "", "", ""),
        Movie("2", "Star wars II", "", "", "", ""),
        Movie("3", "Star wars III", "", "", "", ""),
        Movie("4", "Star wars IV", "", "", "", ""),
    )
}
