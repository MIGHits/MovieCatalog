package com.example.moviecatalog.presentation.view

import android.credentials.CredentialDescription
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import androidx.navigation.navArgs
import coil.compose.AsyncImage
import com.example.moviecatalog.R
import com.example.moviecatalog.R.drawable
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.theme.backgroundColor
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.fadeoutFirst
import com.example.moviecatalog.presentation.theme.fadeoutSecond
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view.navigationBarFragments.FeedScreen
import com.example.moviecatalog.presentation.view.navigationBarFragments.MovieScreen
import com.example.moviecatalog.presentation.view.navigationBarFragments.ProfileScreen
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModel
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModelFactory
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.properties.Delegates

class MovieDetails : ComponentActivity() {
    private lateinit var movieId: String
    private lateinit var viewModel: MovieDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = intent.extras?.getString(MOVIE_ID).toString()
        viewModel = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory()
        ).get(MovieDetailsViewModel::class.java)

        lifecycleScope.launch {
            viewModel.getDetails(movieId).join()
        }

        setContent {
            MovieDetailsScreen(viewModel)
        }
    }

    companion object {
        const val MOVIE_ID = "MovieId"
    }

    private fun navigateBack() {
        finish()
    }


    @Composable
    fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
        val details by remember { viewModel.movieDetails }
        val scrollState = rememberLazyListState()
        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.firstVisibleItemIndex }
                .collect { firstVisibleItemIndex ->
                    isVisible = firstVisibleItemIndex > 0
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            MovieImage(
                details.poster,
                Modifier.align(Alignment.TopCenter)
            )

            details.name?.let { TopBar(it, isVisible) }

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .padding(
                        top = 148.dp,
                        start = 24.dp,
                        end = 24.dp,
                    )
                    .fillMaxSize()
            ) {
                item {
                    Spacer(Modifier.height(263.dp))
                    MovieTittle(
                        details.name.toString(),
                        details.tagline.toString()
                    )
                }
                item {
                    FriendsBlock()
                    details.description?.let { MovieDescription(it) }

                    if (details.mcRating != null) {

                        val symbols = DecimalFormatSymbols(Locale.getDefault())
                        symbols.decimalSeparator = '.'
                        val df = java.text.DecimalFormat("#.#", symbols)

                        MovieRatingBlock(
                            df.format(details.mcRating),
                            details.kinopoiskRating, details.imdbRating
                        )
                    }

                    details.country?.let {
                        MovieMainInfo(
                            it,
                            details.ageLimit,
                            details.time,
                            details.year
                        )
                    }
                    ProducerBlock(
                        details.directorPoster,
                        details.director.toString()
                    )
                    details.genres?.let { GenresBlock(it) }
                    MoneyBlock(
                        details.budget,
                        details.fees
                    )
                    ReviewBlock()
                }
            }
        }
    }

    @Composable
    fun TopBar(movieTittle: String, isVisible: Boolean) {
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
                    painter = painterResource(id = drawable.back_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            if (isVisible) {
                Text(
                    text = movieTittle,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp, bottom = 4.dp),
                    fontSize = 24.sp, color = Color.White
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp, bottom = 4.dp)
                )
            }

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
    }

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

    @Composable
    fun FriendsBlock() {
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
    }

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
                        drawable.mc_rating_logo, mcRating,
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
                            drawable.kp_rating_logo, kinopoiskRating.toString(),
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
                            drawable.imdb_rating_logo, imdbRating.toString(),
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

    @Composable
    fun MovieMainInfo(
        country: String,
        age: Int,
        time: String,
        year: Int
    ) {
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
                            InfoBlock(R.string.countries, country, 0.7f)
                            Spacer(Modifier.width(8.dp))
                            InfoBlock(R.string.age, "$age+", 1f)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            InfoBlock(R.string.time, time.toString(), 0.5f)
                            Spacer(Modifier.width(8.dp))
                            InfoBlock(R.string.release_year, year.toString(), 1f)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ProducerBlock(directorPoster: String? = null, name: String) {
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
                        AsyncImage(
                            model = directorPoster ?: drawable.profile_image,
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
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = name,
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
    }

    @Composable
    fun GenresBlock(genres: List<GenreModelUI>) {
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
                        items(genres) { item ->
                            item.name?.let { Genre(it, false) }
                        }
                    }
                }
            }
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
                        InfoBlock(R.string.budget, "$ $budget", 0.5f)
                        Spacer(Modifier.width(8.dp))
                        InfoBlock(R.string.bank, "$ $fees", 1f)
                    }
                }
            }
        }
    }

    @Composable
    fun ReviewBlock() {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 48.dp)
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                                    bottom = 12.dp
                                )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
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
fun MovieImage(url: String? = null, modifier: Modifier) {

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
        AsyncImage(
            model = url,
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


