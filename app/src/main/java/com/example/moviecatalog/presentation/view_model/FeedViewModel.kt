package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class FeedViewModel(private val getMoviePageUseCase: GetMoviePageUseCase):ViewModel(){
    private val _movieModel = MutableStateFlow<MovieElementModelUI>(MovieElementModelUI())
    val movieModel:StateFlow<MovieElementModelUI> get() = _movieModel

    fun randomMovie(lowerBound:Int,upperBound:Int):Int{
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
}