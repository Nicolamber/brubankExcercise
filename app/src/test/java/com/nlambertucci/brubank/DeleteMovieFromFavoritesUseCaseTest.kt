package com.nlambertucci.brubank

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import com.nlambertucci.brubank.domain.usecase.DeleteMovieFromFavoritesUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DeleteMovieFromFavoritesUseCaseTest {

    @Mock
    private lateinit var repository: FavoritesRepository

    private lateinit var deleteMoviesFromFavoritesUseCase: DeleteMovieFromFavoritesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        deleteMoviesFromFavoritesUseCase = DeleteMovieFromFavoritesUseCase(repository)
    }

    @Test
    fun `test deleteMovie should remove movie from favorites`() {

        val movie = Movie(
            "1", "Avengers: End game", "", "", "", ""
        )

        deleteMoviesFromFavoritesUseCase.deleteMovie(movie)

        verify(repository).deleteMovieFromFavorites(movie)
    }
}