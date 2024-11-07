package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.view.BlockLabel


@Composable
fun MovieRatingBlock(
    mcRating: String? = null,
    kinopoiskRating: Double? = null,
    imdbRating: Double? = null
) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Column {
            BlockLabel(R.drawable.rating_star, R.string.movie_service_rating)
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                MovieRating(
                    R.drawable.mc_rating_logo, mcRating,
                    Modifier
                        .padding()
                        .weight(1.5f)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(colorResource(R.color.screenBackgroundDark))
                )
                if (kinopoiskRating != null) {
                    Spacer(Modifier.width(8.dp))
                    MovieRating(
                        R.drawable.kp_rating_logo, kinopoiskRating.toString(),
                        Modifier
                            .padding()
                            .weight(1f)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(colorResource(R.color.screenBackgroundDark))
                    )
                    Spacer(Modifier.width(8.dp))
                }
                if (imdbRating != null) {
                    MovieRating(
                        R.drawable.imdb_rating_logo, imdbRating.toString(),
                        Modifier
                            .padding()
                            .weight(1f)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(colorResource(R.color.screenBackgroundDark))
                    )
                }
            }
        }
    }
}