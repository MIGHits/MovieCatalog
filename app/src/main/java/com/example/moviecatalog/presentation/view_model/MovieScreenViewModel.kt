package com.example.moviecatalog.presentation.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.mappers.MovieMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.random.Random

class MovieScreenViewModel(
    private val getMoviePageUseCase: GetMoviePageUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val movieMapper: MovieMapper = MovieMapper()
) : ViewModel() {

    private val _moviePage = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val moviePage: StateFlow<List<MovieElementModelUI>?> get() = _moviePage

    private val _favoriteMovies = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val favoriteMovies: StateFlow<List<MovieElementModelUI>?> get() = _favoriteMovies

    private val _movieCollection =
        MutableStateFlow<MutableList<MovieElementModelUI>?>(
            emptyList<MovieElementModelUI>().toMutableList()
        )

    val movieCollection: StateFlow<MutableList<MovieElementModelUI>?> get() = _movieCollection

    private val _blackList =
        MutableStateFlow(
            emptyList<MovieElementModelUI>().toMutableList()
        )

    val blackList:StateFlow<MutableList<MovieElementModelUI>> get() = _blackList

    fun addNewPageToCollection(page: Int) = viewModelScope.launch {
        getMoviePage(page).join()
        val newPage = _moviePage.value?.filter { it !in _blackList.value }
        _movieCollection.value = newPage?.toMutableList()
        _movieCollection.value?.forEach {movie -> banMovie(movie) }
    }

    fun banPage(movies:List<MovieElementModelUI>){
        movies.let { _blackList.value.addAll(it) }
    }

    fun banMovie(movie:MovieElementModelUI){
        movie.let { _blackList.value.add(it) }
    }

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
                    401 -> Log.d("BAD REQUEST", "ERROR")
                }
            }

            else -> Log.d("REQUEST", "1")
        }
    }

    fun getFavorites() = viewModelScope.launch(exceptionHandler) {
        _favoriteMovies.value =
            getFavoriteMoviesUseCase()?.let { movieMapper.map(it) }
    }
}