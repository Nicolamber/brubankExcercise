package com.nlambertucci.brubank.domain.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nlambertucci.brubank.domain.model.Movie
import com.nlambertucci.brubank.domain.repository.MovieRepository
import retrofit2.HttpException

class GetMoviesPaginatedUseCase(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = movieRepository.getMovies(nextPage)
            LoadResult.Page(
                data = response.body()?.movies ?: emptyList(),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.body()?.movies?.isEmpty() == true) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}