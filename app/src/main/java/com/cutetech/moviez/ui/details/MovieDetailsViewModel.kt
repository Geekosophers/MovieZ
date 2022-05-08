package com.cutetech.moviez.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutetech.moviez.data.model.MovieDetails
import com.cutetech.moviez.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieDetails.postValue(moviesRepository.getMovieDetails(movieId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}