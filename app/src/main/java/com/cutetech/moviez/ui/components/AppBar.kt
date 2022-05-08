package com.cutetech.moviez.ui.components

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.cutetech.moviez.ui.theme.DarkBlue

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = DarkBlue
    )
}

@Preview
@Composable
fun AppBarPreview() {
    AppBar(title = "MovieZ")
}