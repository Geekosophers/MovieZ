package com.cutetech.moviez.data.network

import com.cutetech.moviez.BuildConfig
import com.cutetech.moviez.data.model.DiscoverMovie
import com.cutetech.moviez.data.model.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/discover/movie")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1
    ): DiscoverMovie

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieDetails
}