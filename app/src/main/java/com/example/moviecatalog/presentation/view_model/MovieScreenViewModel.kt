package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.mappers.MovieMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MovieScreenViewModel(
    private val getMoviePageUseCase: GetMoviePageUseCase,
    private val movieMapper:MovieMapper = MovieMapper()
):ViewModel() {

    private val _movieModel = MutableStateFlow<MovieElementModelUI>(MovieElementModelUI())
    val movieModel: StateFlow<MovieElementModelUI> get() = _movieModel
    private val _moviePage = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val moviePage:StateFlow<List<MovieElementModelUI>?>get() = _moviePage

    private fun randomMovie(lowerBound:Int, upperBound:Int):Int{
        return Random.nextInt(lowerBound,upperBound)
    }

    fun getMovie() =
        viewModelScope.launch {
            val moviePage = getMoviePageUseCase(randomMovie(1,6))
            val radomMovieIndex = randomMovie(0,moviePage.pageInfo.pageSize)
            val randomMovie = moviePage.movies?.get(radomMovieIndex)
            _movieModel.value = _movieModel.value.copy(
                name = randomMovie?.name,
                poster = randomMovie?.poster,
                country = randomMovie?.country,
                year = randomMovie?.year,
                genres = randomMovie?.genres
            )
        }

    fun getMoviePage(page:Int) = viewModelScope.launch {
        _moviePage.value =
            getMoviePageUseCase(page).movies?.let { movieMapper.map(it) }
    }
}