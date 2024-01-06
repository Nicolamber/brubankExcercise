package com.nlambertucci.brubank

import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.FavoritesRepository
import com.nlambertucci.brubank.domain.usecase.GetFavoritesMoviesUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetFavoritesMoviesUseCaseTest {

    @Mock
    private lateinit var repository: FavoritesRepository

    private lateinit var getFavoritesMoviesUseCaseTest: GetFavoritesMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        getFavoritesMoviesUseCaseTest = GetFavoritesMoviesUseCase(repository)
    }

    @Test
    fun `test getFavorites should return movies if the user has movies already saved`() {

        val mockedFavMovies = mutableListOf<Movie>(
            Movie("1", "Avengers: End game", "", "", "", ""),
            Movie("2", "Harry Potter", "", "", "", ""),
            Movie("3", "Back to the Future", "", "", "", ""),
            Movie("4", "Terminator", "", "", "", ""),
        )
        Mockito.`when`(repository.getUserFavorites()).thenReturn(mockedFavMovies)

        val result = getFavoritesMoviesUseCaseTest.getFavorites()


        assertEquals(mockedFavMovies, result)
    }

    @Test
    fun `test getFavorites when repository returns null`() {
        Mockito.`when`(repository.getUserFavorites()).thenReturn(null)

        val result = getFavoritesMoviesUseCaseTest.getFavorites()

        assertEquals(result, null)
    }
}