package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond


@Composable
fun TopBar(
    movieTittle: String,
    isVisible: Boolean,
    addFavorite: (String) -> Unit,
    deleteFavorite: (String) -> Unit,
    favoritesList: List<MovieElementModelUI>,
    movieId:String,
    navigateBack:()->Unit
) {
    var isFavoriteState by remember { mutableStateOf(false) }
    var favButtonModifier: Modifier = Modifier
        .height(40.dp)
        .width(40.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(darkFaded)

    if (favoritesList.map { it.id }.contains(movieId)) isFavoriteState = true else false

    if (isFavoriteState) favButtonModifier =
        Modifier
            .height(40.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gradient(listOf(gradientFirst, gradientSecond)))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                top = 76.dp,
                end = 24.dp
            )
    ) {
        IconButton(
            onClick = { navigateBack() },
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = darkFaded)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        if (isVisible) {
            Text(
                text = movieTittle,
                modifier = Modifier
                    .offset(y = (-4).dp)
                    .weight(1f)
                    .align(Alignment.Top),
                fontSize = 24.sp, color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp)
            )
        }

        IconButton(
            onClick = {
                if (isFavoriteState) {
                    deleteFavorite(movieId)
                    isFavoriteState = false
                } else {
                    addFavorite(movieId)
                    isFavoriteState = true
                }
            },
            modifier = favButtonModifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.heart_light_ic),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}