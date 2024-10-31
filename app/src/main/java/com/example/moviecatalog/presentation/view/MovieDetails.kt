package com.example.moviecatalog.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.fragment.fragment
import com.example.moviecatalog.R
import com.example.moviecatalog.R.drawable
import com.example.moviecatalog.presentation.theme.backgroundColor
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.fadeoutFirst
import com.example.moviecatalog.presentation.theme.fadeoutSecond
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond

class MovieDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}



@Preview
@Composable
fun MovieDetailsScreen() {
    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        MovieImage("", Modifier.align(Alignment.TopCenter))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp
                )
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = darkFaded)

            ) {
                Icon(
                    painter = painterResource(id = drawable.back_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Название Фильма",
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp, bottom = 4.dp),
                fontSize = 24.sp, color = Color.White
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = darkFaded)

            ) {
                Icon(
                    painter = painterResource(id = drawable.heart_light_ic),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    top = 411.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 48.dp
                )
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Gradient(
                            listOf(
                                gradientFirst,
                                gradientSecond
                            )
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
                        "1899",
                        fontSize = 36.sp,
                        lineHeight = 50.4.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "What is lost will be found",
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Box() {
                        val list = listOf(
                            drawable.active_profile,
                            drawable.profile_image,
                            drawable.profile_image
                        )
                        LazyRow() { items(list) { item -> FriendsAvatar(item) } }
                    }
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 6.dp, bottom = 6.dp),
                        text = "нравится n вашим друзьям",
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 20.sp
                    )
                }
            }
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
                    text = "Группа европейских мигрантов покидает Лондон на пароходе, чтобы начать новую жизнь в Нью-Йорке. Когда они сталкиваются с другим судном, плывущим по течению в открытом море, их путешествие превращается в кошмар"
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.rating_star, R.string.movie_service_rating)
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
                            drawable.mc_rating_logo, 9.7f,
                            Modifier
                                .padding()
                                .weight(1.5f)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background(colorResource(R.color.screenBackgroundDark))
                        )
                        Spacer(Modifier.width(8.dp))
                        MovieRating(
                            drawable.kp_rating_logo, 9.7f,
                            Modifier
                                .padding()
                                .weight(1f)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background(colorResource(R.color.screenBackgroundDark))
                        )
                        Spacer(Modifier.width(8.dp))
                        MovieRating(
                            drawable.imdb_rating_logo, 9.7f,
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
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.info_ic, R.string.info)
                    Box(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.Top) {
                                InfoBlock(R.string.countries, "Германия, США", 0.7f)
                                Spacer(Modifier.width(8.dp))
                                InfoBlock(R.string.age, "16+", 1f)
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                InfoBlock(R.string.time, "1 ч 30 мин", 0.5f)
                                Spacer(Modifier.width(8.dp))
                                InfoBlock(R.string.release_year, "2022", 1f)
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.budget_ic, R.string.producer)
                    Box(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorResource(R.color.screenBackgroundDark))
                        ) {
                            Image(
                                painter = painterResource(
                                    id = drawable.baran_odar
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(
                                        start = 12.dp,
                                        top = 8.dp,
                                        bottom = 8.dp,
                                        end = 8.dp
                                    )
                                    .width(48.dp)
                                    .height(48.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = "Баран бо Одар",
                                color = Color.White,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.genres_ic, R.string.genres)
                    Box(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(listOf("Жанр 1", "Жанр 2", "Жанр 3", "Жанр 4")) { item ->
                                Genre(item, false)
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.budget_ic, R.string.budget_category)
                    Box(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 12.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                    ) {
                        Row {
                            InfoBlock(R.string.budget, "$ 15 000 000", 0.5f)
                            Spacer(Modifier.width(8.dp))
                            InfoBlock(R.string.bank, "$ 30 000 000", 1f)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(darkFaded)
            ) {
                Column {
                    BlockLabel(drawable.review_ic, R.string.reviews)
                    Box(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 12.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .background(colorResource(R.color.screenBackgroundDark))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                FriendsAvatar(drawable.profile_image)
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = 8.dp,
                                            end = 8.dp
                                        )
                                        .fillMaxWidth(0.75f)
                                ) {
                                    Text(
                                        text = "Анонимный пользователь",
                                        fontSize = 12.sp,
                                        lineHeight = 14.4.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "17 октября 2024",
                                        fontSize = 12.sp,
                                        lineHeight = 14.4.sp,
                                        color = colorResource(R.color.GrayFaded)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(colorResource(R.color.rating9_9))
                                ) {
                                    Icon(
                                        painter = painterResource(drawable.review_rating_star),
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
                                        text = "10",
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
                            Text(
                                "Если у вас взорвался мозг от «Тьмы» и вам это понравилось, то не переживайте. Новый сериал Барана бо Одара «1899» получился не хуже предшественника",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp,
                                        start = 12.dp,
                                        end = 12.dp,
                                        bottom = 12.dp
                                    )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = 12.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                            .fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {},
                            modifier = Modifier
                                .padding(end = 24.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Gradient(
                                        listOf(
                                            gradientFirst,
                                            gradientSecond
                                        )
                                    )
                                )
                                .weight(3f)
                        ) {
                            Text(
                                text = "Добавить отзыв",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = darkFaded)

                        ) {
                            Icon(
                                painter = painterResource(id = drawable.back_icon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(color = colorResource(R.color.screenBackgroundDark))

                        ) {
                            Icon(
                                painter = painterResource(id = drawable.forward_ic),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Gradient(gradientColors: List<Color>): Brush {

    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(0f, 0f),
        end = Offset(0f, 1000f)
    )

    return brush
}

@Composable
fun Genre(name: String, isFavorite: Boolean) {
    val gradient = Gradient(
        listOf(
            gradientFirst,
            gradientSecond
        )
    )


    if (isFavorite) {
        Text(
            text = name,
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

        )
    } else {
        Text(
            text = name,
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
        )
    }
}

@Composable
fun InfoBlock(category: Int, text: String, size: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth(size)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.screenBackgroundDark))
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp)
    ) {
        Text(
            text = stringResource(category),
            color = colorResource(R.color.TextGray),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BlockLabel(icon: Int, text: Int) {
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colorResource(R.color.gray_scale)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = stringResource(text),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = colorResource(R.color.gray_scale)
        )
    }
}

@Composable
fun MovieRating(logo: Int, rating: Float = 9.5f, modifier: Modifier) {
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
fun FriendsAvatar(avatar: Int) {
    Image(
        painter = painterResource(
            id = avatar
        ),
        contentDescription = null,
        modifier = Modifier
            .width(32.dp)
            .height(32.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun MovieImage(url: String = "", modifier: Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(464.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
    ) {
        Image(
            painter = painterResource(
                id = drawable.movie_details_poster
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.345f)
                .background(
                    Gradient(
                        listOf(
                            fadeoutFirst,
                            fadeoutSecond
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )

    }
}


@Composable
fun VisibilityTrackerExample() {
    var isVisible by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Проверка видимости",
            modifier = Modifier
                .padding(16.dp)
                .onGloballyPositioned { coordinates ->
                    val visibleRect = Rect(
                        0f,
                        0f,
                        coordinates.size.width.toFloat(),
                        coordinates.size.height.toFloat()
                    )
                    val parentRect = coordinates.parentLayoutCoordinates?.boundsInRoot()

                    isVisible = parentRect?.intersect(visibleRect) != null
                }
        )

        if (isVisible) {
            Text(text = "Элемент виден")
        } else {
            Text(text = "Элемент не виден")
        }
    }
}

@Preview
@Composable
fun LazyRowWithWeightExample() {
    val items = listOf("Элемент 1", "Элемент 2", "Элемент 3")

    Row {
        Box(
            modifier = Modifier
                .background(Color.Red)
                .height(150.dp)
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .height(150.dp)
                .weight(3f)
        )
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .height(150.dp)
                .weight(2f)
        )
    }
}