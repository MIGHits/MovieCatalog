package com.example.moviecatalog.presentation.view.MovieDetailsComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.view.BlockLabel
import com.example.moviecatalog.presentation.view.InfoBlock

@Composable
fun MovieDescription(description: String) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = Color.White,
            text = description
        )
    }
}


@Composable
fun MoneyBlock(budget: Int?, fees: Int?) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(darkFaded)
    ) {
        Column {
            BlockLabel(R.drawable.budget_ic, R.string.budget_category)
            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Row {
                    InfoBlock(R.string.budget, "$ $budget", 0.5f)
                    Spacer(Modifier.width(8.dp))
                    InfoBlock(R.string.bank, "$ $fees", 1f)
                }
            }
        }
    }
}


@Composable
fun ReviewRating(rating: Int) {
    var color = colorResource(R.color.white)
    when (rating) {
        in 0..<1 -> color = colorResource(R.color.GrayFaded)
        in 1..<2 -> color = colorResource(R.color.rating1_0)
        in 2..<3 -> color = colorResource(R.color.rating2_3)
        in 3..<4 -> color = colorResource(R.color.rating3_6)
        in 4..<6 -> color = colorResource(R.color.rating4_0)
        in 6..<7 -> color = colorResource(R.color.rating6_0)
        in 7..<8 -> color = colorResource(R.color.rating7_4)
        in 8..<9 -> color = colorResource(R.color.rating8_5)
        in 9..10 -> color = colorResource(R.color.rating9_9)
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color)
    ) {
        Icon(
            painter = painterResource(R.drawable.review_rating_star),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(
                start = 8.dp,
                top = 6.dp,
                bottom = 6.dp,
                end = 4.dp
            )
        )
        Text(
            text = rating.toString(),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = Color.White,
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    end = 8.dp
                )
        )
    }
}

@Composable
fun Gradient(
    gradientColors: List<Color>,
    vertical: Boolean = false
): Brush {

    var brush: Brush
    if (vertical) {
        brush = Brush.verticalGradient(
            colors = gradientColors,
            endY = 400f,
            startY = 0f
        )
    } else {
        brush = Brush.linearGradient(
            colors = gradientColors,
            start = Offset.Zero,
            end = Offset.Infinite
        )
    }
    return brush
}

@Composable
fun MovieRating(logo: Int, rating: String? = null, modifier: Modifier) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            Image(
                painter = painterResource(logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        end = 8.dp
                    )
            )
            Text(
                text = rating.toString(),
                modifier = Modifier
                    .padding(
                        end = 12.dp
                    ),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun SliderLabel(label: String, minWidth: Dp, modifier: Modifier = Modifier) {
    Text(
        label,
        color = Color.White,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(
                color = darkFaded,
                shape = CircleShape
            )
            .padding(4.dp)
            .defaultMinSize(minWidth = minWidth)
    )
}