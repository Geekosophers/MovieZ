package com.cutetech.moviez.data.repository

import com.cutetech.moviez.data.network.ApiService

class MoviesRepository(private val apiService: ApiService) {

    suspend fun getAllMovies(page: Int) = apiService.getAllMovies(page = page)

    suspend fun getMovieDetails(movieId: Int) = apiService.getMovieDetails(movieId = movieId)
}