package com.cutetech.moviez.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cutetech.moviez.data.repository.MoviesRepository

class MoviePagingSource(private val moviesRepository: MoviesRepository) : PagingSource<Int, DiscoverMovie.Result>() {
    override fun getRefreshKey(state: PagingState<Int, DiscoverMovie.Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovie.Result> {
        return try {
            val nextPage = params.key ?: 1
            val moviesList = moviesRepository.getAllMovies(nextPage)
            LoadResult.Page(
                data = moviesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (moviesList.results.isEmpty()) null else moviesList.page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}