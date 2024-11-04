package com.example.moviecatalog.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.moviecatalog.R
import com.example.moviecatalog.R.drawable
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.ReviewModelUI
import com.example.moviecatalog.presentation.theme.backgroundColor
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.fadeoutFirst
import com.example.moviecatalog.presentation.theme.fadeoutSecond
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModel
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModelFactory
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.roundToInt

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
                        top = 160.dp,
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
                    details.reviews?.let { ReviewBlock(it) }
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
                        .padding(top = 4.dp, bottom = 4.dp)
                        .align(Alignment.Top),
                    fontSize = 24.sp, color = Color.White,
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
                    LazyRow() { items(list) { item -> FriendsAvatar(item.toString()) } }
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
    fun ReviewBlock(reviews: List<ReviewModelUI>) {
        val reviewsCount = reviews.size
        var current by remember { mutableIntStateOf(0) }
        var reviewDialogState by remember { mutableStateOf(false) }

        if (reviewDialogState) {
            MyDialog { reviewDialogState = false }
        }

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

                            if (!reviews[current].isAnonymous) {
                                FriendsAvatar(reviews[current].author.avatar.toString())
                            } else {
                                FriendsAvatar(INITIAL_FIELD_STATE)
                            }

                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        end = 8.dp
                                    )
                                    .fillMaxWidth(0.75f)
                            ) {
                                Text(
                                    text = if (reviews[current].isAnonymous) stringResource(R.string.anonimus)
                                    else reviews[current].author.nickName.toString(),
                                    fontSize = 12.sp,
                                    lineHeight = 14.4.sp,
                                    color = Color.White
                                )
                                Text(
                                    text = reviews[current].createDateTime,
                                    fontSize = 12.sp,
                                    lineHeight = 14.4.sp,
                                    color = colorResource(R.color.GrayFaded)
                                )
                            }
                            ReviewRating(reviews[current].rating)
                        }
                        Text(
                            text = reviews[current].reviewText.toString(),
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
                        onClick = { reviewDialogState = true },
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
                        onClick = { current-- },
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (current != 0) colorResource(R.color.screenBackgroundDark)
                                else darkFaded
                            ),
                        enabled = current != 0

                    ) {
                        Icon(
                            painter = painterResource(id = drawable.back_icon),
                            contentDescription = null,
                            tint = if (current != 0) Color.White
                            else colorResource(R.color.TextGray),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    IconButton(
                        onClick = { current++ },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (current != reviewsCount - 1) colorResource(R.color.screenBackgroundDark)
                                else darkFaded
                            ),
                        enabled = current != reviewsCount - 1

                    ) {
                        Icon(
                            painter = painterResource(id = drawable.forward_ic),
                            contentDescription = null,
                            tint = if (current != reviewsCount - 1) Color.White
                            else colorResource(R.color.TextGray),
                            modifier = Modifier.padding(8.dp)
                        )
                    }

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
fun Gradient(gradientColors: List<Color>): Brush {

    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset.Zero,
        end = Offset.Infinite
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
fun FriendsAvatar(userAvatar: String) {
    val guestAvatar = drawable.profile_image
    AsyncImage(
        model = userAvatar.ifEmpty { guestAvatar },
        contentDescription = null,
        modifier = Modifier
            .width(32.dp)
            .height(32.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MovieImage(url: String? = null, modifier: Modifier) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDialog(onDismiss: () -> Unit) {
    var sliderPos by remember { mutableFloatStateOf(5f) }
    var textField by remember { mutableStateOf("") }
    var anonimusCheck by remember { mutableStateOf(false) }
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .clip(RoundedCornerShape(28.dp)),
        backgroundColor = colorResource(R.color.screenBackgroundDark),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Добавить отзыв",
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        text = {
            Column {

                Text(
                    text = "Оценка",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = colorResource(R.color.TextGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )

                BoxWithConstraints() {


                    val offset = getSliderOffset(
                        value = sliderPos,
                        valueRange = 0f..10f,
                        boxWidth = maxWidth,
                        labelWidth = 24.dp + 8.dp
                    )

                    if (sliderPos >= 0) {
                        SliderLabel(
                            label = sliderPos.roundToInt().toString(),
                            minWidth = 24.dp,
                            modifier = Modifier
                                .padding(start = offset)
                        )
                    }
                }

                androidx.compose.material3.Slider(
                    value = sliderPos,
                    onValueChange = { sliderPos = it },
                    valueRange = 0f..10f,
                    steps = 9,
                    colors = androidx.compose.material3.SliderDefaults.colors(
                        activeTickColor = Color.White,
                        inactiveTickColor = colorResource(R.color.Accent),
                        activeTrackColor = colorResource(R.color.Accent),
                        inactiveTrackColor = darkFaded
                    ),
                    thumb = {
                        Icon(
                            painter = painterResource(drawable.thumb),
                            contentDescription = null,
                            tint = colorResource(R.color.Accent)
                        )
                    }
                )
                TextField(
                    value = textField,
                    onValueChange = { textField = it },
                    placeholder = {
                        Column {
                            Text(
                                stringResource(R.string.reviewText),
                                color = colorResource(R.color.GrayFaded),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.height(84.dp))
                        }
                    },
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.DarkFaded),
                        unfocusedContainerColor = colorResource(R.color.DarkFaded),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = colorResource(R.color.Accent)
                    ),
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(darkFaded)
                )
                Row(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.anonReview),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = colorResource(R.color.GrayFaded),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.width(160.dp))
                    Switch(
                        checked = anonimusCheck,
                        onCheckedChange = { anonimusCheck = it },
                        colors = androidx.compose.material3.SwitchDefaults.colors(
                            checkedTrackColor = colorResource(R.color.Accent),
                            uncheckedTrackColor = colorResource(R.color.DarkFaded),
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .offset((-12).dp, (-12).dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Gradient(
                            listOf(
                                gradientFirst,
                                gradientSecond
                            )
                        )
                    )
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                Text(
                    stringResource(R.string.sendReview),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding()
                )
            }
        }
    )
}

private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp
): Dp {

    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}

private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

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