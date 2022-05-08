package com.cutetech.moviez.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cutetech.moviez.R
import com.cutetech.moviez.data.model.MovieDetails
import com.cutetech.moviez.ui.components.RatingBar
import com.cutetech.moviez.ui.theme.DarkBlue
import com.cutetech.moviez.ui.theme.GrayButton
import org.koin.androidx.compose.viewModel

class MovieDetailsActivity : ComponentActivity() {

    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = intent.getIntExtra(MOVIE_ID, -1)
        setContent {
            val movieDetailsViewModel: MovieDetailsViewModel by viewModel()
            if (movieId != -1) {
                LaunchedEffect(key1 = movieId) {
                    movieDetailsViewModel.fetchMovieDetails(movieId)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                movieDetailsViewModel.movieDetails.observeAsState().value?.let { movieDetails ->
                    MovieDetailsScreen(movieDetails = movieDetails)
                }

                IconButton(
                    onClick = ::onBackPressed,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                        contentDescription = "Back"
                    )
                }
            }
        }
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun launch(context: Context, movieId: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}

@Composable
fun MovieDetailsScreen(movieDetails: MovieDetails) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original${movieDetails.backdropPath}")
                .crossfade(true)
                .build(),
            contentDescription = "Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        )

        Text(
            text = movieDetails.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, end = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GrayButton,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                shape = RoundedCornerShape(100)
            ) {
                Text(text = "WATCH NOW")
            }

            Spacer(modifier = Modifier.weight(1f))

            RatingBar(
                rating = (movieDetails.voteAverage.toFloat() / 2),
                modifier = Modifier.height(16.dp)
            )
        }

        Text(
            text = movieDetails.overview,
            color = Color.LightGray,
            fontSize = 16.sp,
            lineHeight = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, end = 16.dp)
        )

        DetailsRow(
            key = "Genre",
            value = movieDetails.getGenres(),
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp)
        )

        if (movieDetails.getReleaseYear().isNotBlank()) {
            DetailsRow(
                key = "Release",
                value = movieDetails.getReleaseYear(),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
            )
        }
    }
}

@Composable
private fun DetailsRow(
    key: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = key,
            color = Color.White,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = value,
            color = Color.LightGray,
            fontSize = 16.sp,
        )
    }
}