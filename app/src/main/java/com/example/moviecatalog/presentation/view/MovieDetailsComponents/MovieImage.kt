package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviecatalog.presentation.theme.fadeoutFirst
import com.example.moviecatalog.presentation.theme.fadeoutSecond

@Composable
fun MovieImage(url: String? = null, modifier: Modifier, isVisible: Boolean) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.53f)
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (isVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.345f)
                    .background(
                        Gradient(
                            listOf(
                                fadeoutSecond,
                                fadeoutFirst
                            ),
                            vertical = true
                        )
                    )
                    .align(Alignment.TopCenter)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.345f)
                .background(
                    Gradient(
                        listOf(
                            fadeoutFirst,
                            fadeoutSecond
                        ),
                        vertical = true
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }
}