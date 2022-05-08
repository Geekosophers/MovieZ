package com.cutetech.moviez.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cutetech.moviez.data.model.DiscoverMovie
import com.cutetech.moviez.ui.components.AppBar
import com.cutetech.moviez.ui.components.MovieCard
import com.cutetech.moviez.ui.details.MovieDetailsActivity
import com.cutetech.moviez.ui.theme.DarkBlue
import com.cutetech.moviez.ui.theme.MovieZTheme
import com.cutetech.moviez.ui.theme.SkyBlue
import org.koin.androidx.compose.viewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel: HomeViewModel by viewModel()
            val movieListItems: LazyPagingItems<DiscoverMovie.Result> =
                homeViewModel.movies.collectAsLazyPagingItems()

            Scaffold(
                topBar = { AppBar(title = "MovieZ") },
                modifier = Modifier.fillMaxSize(),
                backgroundColor = DarkBlue
            ) {
                if (homeViewModel.isLoading.value) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = SkyBlue
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items(movieListItems.itemCount) { key ->
                            movieListItems[key]?.let { movie ->
                                MovieCard(
                                    name = movie.originalTitle,
                                    posterUrl = movie.posterPath,
                                    rating = movie.voteAverage / 2
                                ) {
                                    MovieDetailsActivity.launch(this@HomeActivity, movie.id)
                                }
                            }
                        }
                    }
                }
            }

            movieListItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        homeViewModel.isLoading.value = true
                    }

                    loadState.append is LoadState.Loading -> {

                    }

                    loadState.refresh is LoadState.NotLoading -> {
                        homeViewModel.isLoading.value = false
                    }

                    loadState.append is LoadState.Error -> {

                    }
                }
            }
        }
    }
}
