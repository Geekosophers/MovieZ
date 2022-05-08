package com.cutetech.moviez.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieCard(
    name: String,
    posterUrl: String,
    rating: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w300/$posterUrl")
                .crossfade(true)
                .build(),
            contentDescription = "Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(2 / 3f)
                .clip(RoundedCornerShape(16.dp))
        )

        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentWidth()
        )

        RatingBar(rating = rating.toFloat(), modifier = Modifier
            .height(12.dp)
            .padding(top = 4.dp))
    }
}