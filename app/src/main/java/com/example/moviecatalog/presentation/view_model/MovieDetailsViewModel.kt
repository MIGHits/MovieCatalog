package com.example.moviecatalog.presentation.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.presentation.entity.MovieDetailsModelUI
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val movieDetailsMapperUI: MovieDetailsMapperUI,
    private val getMovieRatings: GetMovieRatingsUseCase,
    private val getPoster: GetDirectorPosterUseCase
) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsModelUI> =
        mutableStateOf(MovieDetailsModelUI())
    val movieDetails: State<MovieDetailsModelUI> get() = _movieDetails

    fun getDetails(id: String) = viewModelScope.launch {
        _movieDetails.value =
            movieDetailsMapperUI.map(getMovieDetails(id))

        getRatings(
            _movieDetails.value.name.toString(),
            _movieDetails.value.year
        ).join()

        getDirectorPoster(_movieDetails.value.director.toString()).join()
    }

    fun getRatings(name: String, year: Int) = viewModelScope.launch {
        val ratings = getMovieRatings(name, year)
        _movieDetails.value = _movieDetails.value.copy(
            kinopoiskRating = ratings.ratingKinopoisk,
            imdbRating = ratings.ratingImdb
        )
    }

    fun getDirectorPoster(name: String) = viewModelScope.launch {
        val poster = getPoster(name)
        _movieDetails.value = _movieDetails.value.copy(directorPoster = poster)
    }

}