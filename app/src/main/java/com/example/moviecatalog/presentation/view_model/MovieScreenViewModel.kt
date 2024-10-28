package com.example.moviecatalog.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.EXCEPTION_ERROR
import com.example.moviecatalog.common.Constants.UNIQUE_LOGIN_ERROR
import com.example.moviecatalog.domain.entity.MoviesPagedListModel
import com.example.moviecatalog.domain.usecase.AddFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase
import com.example.moviecatalog.domain.usecase.RemoveFavoriteMoviesUseCase
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.mappers.MovieMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.random.Random

class MovieScreenViewModel(
    private val getMoviePageUseCase: GetMoviePageUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val addFavoriteMoviesUseCase: AddFavoriteMoviesUseCase,
    private val removeFavoriteMoviesUseCase: RemoveFavoriteMoviesUseCase,
    private val movieMapper: MovieMapper = MovieMapper()
) : ViewModel() {

    private val _moviePage = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val moviePage: StateFlow<List<MovieElementModelUI>?> get() = _moviePage

    private val _favoriteMovies = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val favoriteMovies:StateFlow<List<MovieElementModelUI>?> get() = _favoriteMovies

    fun randomMovie(lowerBound: Int, upperBound: Int): Int {
        return Random.nextInt(lowerBound, upperBound)
    }

    fun getMoviePage(page: Int) = viewModelScope.launch {
        _moviePage.value =
            getMoviePageUseCase(page).movies?.let { movieMapper.map(it) }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    401 -> Log.d("BAD REQUEST","ERROR")
                }
            }

            else -> Log.d("REQUEST","1")
        }
    }

    fun addFavorites(id:String) = viewModelScope.launch (exceptionHandler){
        addFavoriteMoviesUseCase(id)
    }

    fun getFavorites() = viewModelScope.launch {
        _favoriteMovies.value =
            getFavoriteMoviesUseCase()?.let { movieMapper.map(it) }
    }

    fun removeFavorites(id:String) = viewModelScope.launch (exceptionHandler){
        removeFavoriteMoviesUseCase(id)
    }
}