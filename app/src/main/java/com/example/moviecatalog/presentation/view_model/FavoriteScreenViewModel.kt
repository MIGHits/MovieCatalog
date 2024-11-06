package com.example.moviecatalog.presentation.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.data.data.mappers.GenreMapper
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteMoviesUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.mappers.MovieMapper
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    private val genreMapper: com.example.moviecatalog.presentation.mappers.GenreMapper,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase,
    private val getProfile: GetUserProfileDataUseCase,
    private val profileMapperUI: ProfileUIMapper,
    private val getMovies: GetFavoriteMoviesUseCase,
    private val movieMapper: MovieMapper,
    private val deleteFavoriteGenre: DeleteFavoriteGenreUseCase
) :
    ViewModel() {
    private val _userProfile = MutableStateFlow(ProfileModelUI())

    private val _favorites = MutableStateFlow<List<GenreModelUI>>(emptyList())
    val favorites: StateFlow<List<GenreModelUI>> get() = _favorites

    private val _movies = MutableStateFlow<List<MovieElementModelUI>?>(emptyList())
    val movies: StateFlow<List<MovieElementModelUI>?> get() = _movies

    init {
        viewModelScope.launch {
            getUserData().join()
            getFavoriteGenres()
            getFavorites()
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        _movies.value = getMovies()?.let { movieMapper.map(it) }
    }

    private fun getUserData() = viewModelScope.launch {
        _userProfile.value = profileMapperUI.map(getProfile())
    }

    private fun getFavoriteGenres() = viewModelScope.launch(Dispatchers.IO) {
        genreMapper.flowMap(getFavoriteGenresUseCase(_userProfile.value.id)).collect { genres ->
            _favorites.value = genres
        }
    }

    fun deleteGenre(genreId: String) = viewModelScope.launch {
        deleteFavoriteGenre(_userProfile.value.id, genreId)
    }
}