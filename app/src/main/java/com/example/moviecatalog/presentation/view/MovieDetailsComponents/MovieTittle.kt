package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond

@Composable
fun MovieTittle(movieTittle: String, tagline: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Gradient(
                    listOf(
                        gradientFirst,
                        gradientSecond
                    ),
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                bottom = 16.dp,
                top = 16.dp,
                end = 16.dp
            )
        ) {
            Text(
                text = movieTittle,
                fontSize = 36.sp,
                lineHeight = 50.4.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = tagline,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}