package com.cutetech.moviez.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cutetech.moviez.data.model.DiscoverMovie
import com.cutetech.moviez.data.model.MoviePagingSource
import com.cutetech.moviez.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val isLoading = mutableStateOf(false)

    val movies: Flow<PagingData<DiscoverMovie.Result>> = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(moviesRepository)
    }.flow.cachedIn(viewModelScope)
}