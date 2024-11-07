package com.example.moviecatalog.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.moviecatalog.R
import com.example.moviecatalog.R.drawable
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.ReviewModelUI
import com.example.moviecatalog.presentation.entity.UserReviewUI
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.theme.backgroundColor
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.fadeoutFirst
import com.example.moviecatalog.presentation.theme.fadeoutSecond
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.FriendsBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.GenresBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MoneyBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MovieDescription
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MovieImage
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MovieMainInfo
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MovieRatingBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.MovieTittle
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.ProducerBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.ReviewBlock
import com.example.moviecatalog.presentation.view.MovieDetailsComponents.TopBar
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModel
import com.example.moviecatalog.presentation.view_model.MovieDetailsViewModelFactory
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.roundToInt

class MovieDetails : ComponentActivity() {
    private lateinit var movieId: String
    private lateinit var viewModel: MovieDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

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

    fun navigateBack() {
        finish()
    }

    @Composable
    fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
        val details by remember { viewModel.movieDetails }
        val user by remember { viewModel.userProfile }
        val favoriteMovies by remember { viewModel.favoriteMovies }
        val favorites = viewModel.favorites?.collectAsState(emptyList())
        val friends = viewModel.friends?.collectAsState(emptyList())
        val scrollState = rememberLazyListState()
        var isVisible by remember { mutableStateOf(false) }

        val addFavorite: (String) -> Unit = { movie -> viewModel.addFavoriteMovie(movie) }
        val deleteFavorite: (String) -> Unit = { movie -> viewModel.deleteFavoriteMovie(movie) }

        val handleFriendAdd: (UserShortModelUI) -> Unit = { friend ->
            viewModel.addFriend(friend)
        }

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.firstVisibleItemIndex }
                .distinctUntilChanged()
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
                Modifier.align(Alignment.TopCenter),
                isVisible
            )

            details.name?.let {
                TopBar(
                    it,
                    isVisible,
                    addFavorite,
                    deleteFavorite,
                    favoriteMovies,
                    movieId
                ) { navigateBack() }
            }

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
                    friends?.let { FriendsBlock(it.value, handleFriendAdd) }
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
                    details.genres?.let {
                        GenresBlock(
                            it,
                            favorites?.value ?: emptyList(),
                            viewModel
                        )
                    }
                    MoneyBlock(
                        details.budget,
                        details.fees
                    )
                    details.reviews?.let {
                        ReviewBlock(
                            it,
                            details.id,
                            user.id,
                            handleFriendAdd,
                            viewModel
                        )
                    }
                }
            }
        }
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





