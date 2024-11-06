package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.theme.darkFaded
import com.example.moviecatalog.presentation.theme.gradientFirst
import com.example.moviecatalog.presentation.theme.gradientSecond
import com.example.moviecatalog.presentation.view.utils.Separator
import com.example.moviecatalog.presentation.view.utils.getRatingColor
import com.example.moviecatalog.presentation.view_model.FavoriteScreenViewModel
import com.example.moviecatalog.presentation.view_model.FavoriteScreenViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteScreen : Fragment() {
    private lateinit var viewModel: FavoriteScreenViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            this,
            FavoriteScreenViewModelFactory()
        )[FavoriteScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val deleteGenreCb: (String) -> Unit = { genreId -> viewModel.deleteGenre(genreId) }
        return ComposeView(requireContext()).apply {
            setContent {
                Favorites(viewModel, deleteGenreCb)
            }
        }
    }
}


@Composable
fun Favorites(viewModel: FavoriteScreenViewModel, deleteGenre: (String) -> Unit) {
    val scrollState = rememberLazyListState()
    val favoriteGenres by viewModel.favorites.collectAsState()
    val favoriteMovies by viewModel.movies.collectAsState()
    var columnSize by remember { mutableStateOf(Size.Zero) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutCoordinates ->
                columnSize = layoutCoordinates.size.toSize()
            }
            .background(colorResource(R.color.screenBackgroundDark)),
        state = scrollState
    ) {
        item {
            Text(
                text = "Избранное",
                fontSize = 24.sp,
                color = Color.White,
                lineHeight = 32.sp,
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        top = 76.dp
                    )
            )
            Box(
                modifier = Modifier.padding(
                    top = 32.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
            ) {
                Column {
                    Text(
                        text = "Любимые жанры",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            brush = Gradient(
                                listOf(
                                    gradientFirst,
                                    gradientSecond
                                )
                            )
                        ), modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                    )
                    LazyColumn(
                        userScrollEnabled = false,
                        modifier = Modifier
                            .wrapContentSize(unbounded = false)
                            .heightIn(0.dp,
                                LocalDensity.current.run { (columnSize.height).toDp() })
                    ) {
                        favoriteGenres.let {
                            items(
                                it
                            ) { item ->
                                FavoriteGenre(item, deleteGenre)
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp
                    )
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Любимые фильмы",
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            brush = Gradient(
                                listOf(
                                    gradientFirst,
                                    gradientSecond
                                )
                            )
                        )
                    )
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(bottom = 96.dp)
                            .wrapContentSize(unbounded = false)
                            .heightIn(0.dp,
                                LocalDensity.current.run { (columnSize.height).toDp() }),
                        columns = GridCells.Fixed(3),
                        userScrollEnabled = false
                    ) {
                        items(favoriteMovies!!) { item -> MovieCard(item) }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: MovieElementModelUI) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .wrapContentSize()
    ) {
        AsyncImage(
            model = movie.poster,
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = Separator(movie.reviews),
            color = Color.White,
            fontSize = 12.sp,
            lineHeight = 14.4.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(colorResource(getRatingColor(movie.reviews)))
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
        )
    }
}


@Composable
fun FavoriteGenre(genre: GenreModelUI, deleteGenre: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.DarkFaded))
            .padding(
                start = 12.dp,
                top = 8.dp,
                end = 12.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = genre.name.toString(),
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = { deleteGenre(genre.id) },
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(R.color.screenBackgroundDark))

        ) {
            Icon(
                painter = painterResource(id = R.drawable.broken_heart),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun Gradient(
    gradientColors: List<Color>
): Brush {
    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    return brush
}