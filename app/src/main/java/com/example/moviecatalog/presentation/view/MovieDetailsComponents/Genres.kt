package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view.BlockLabel
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModel

@Composable
fun GenresBlock(genres: List<GenreModelUI>, favorites: List<GenreModelUI>,viewModel: MovieDetailsViewModel) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Column {
            BlockLabel(R.drawable.genres_ic, R.string.genres)
            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(genres) { item ->
                        Genre(item, favorites.contains(item), viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun Genre(genre: GenreModelUI, isFavorite: Boolean, viewModel: MovieDetailsViewModel) {
    val gradient = Gradient(
        listOf(
            gradientFirst,
            gradientSecond
        ),
    )


    if (isFavorite) {
        Text(
            text = genre.name.toString(),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(gradient)
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 12.dp
                )
                .clickable {
                    viewModel.deleteFavoritegenre(genre.id)
                }
        )
    } else {
        Text(
            text = genre.name.toString(),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = Color.White,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(R.color.screenBackgroundDark))
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 12.dp
                )
                .clickable {
                    viewModel.addFavoriteGenre(genre)
                }
        )
    }
}