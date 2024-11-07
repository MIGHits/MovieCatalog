package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetMoviePageUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.mappers.GenreMapper
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class FeedViewModel(
    private val getMoviePageUseCase: GetMoviePageUseCase,
    private val genreMapper: GenreMapper = GenreMapper(),
    private val getFavorites: GetFavoriteGenresUseCase,
    private val getProfile:GetUserProfileDataUseCase,
    private val profileUIMapper: ProfileUIMapper
) : ViewModel() {
    private val _movieModel =
        MutableStateFlow(MovieElementModelUI(id = INITIAL_FIELD_STATE))
    val movieModel: StateFlow<MovieElementModelUI> get() = _movieModel

    private val _favorites = MutableStateFlow<List<GenreModelUI>>(emptyList())
    val favorites: StateFlow<List<GenreModelUI>> get() = _favorites

    private val _profile = MutableStateFlow(ProfileModelUI())
    val profile: StateFlow<ProfileModelUI> get() = _profile

    private fun randomMovie(lowerBound: Int, upperBound: Int): Int {
        return Random.nextInt(lowerBound, upperBound)
    }


    fun getMovie() =
        viewModelScope.launch {
            val moviePage = getMoviePageUseCase(randomMovie(1, 6))
            val radomMovieIndex = randomMovie(0, moviePage.pageInfo.pageSize)
            val randomMovie = moviePage.movies?.get(radomMovieIndex)
            _movieModel.value = _movieModel.value.copy(
                name = randomMovie?.name,
                id = randomMovie?.id ?: INITIAL_FIELD_STATE,
                poster = randomMovie?.poster,
                country = randomMovie?.country,
                year = randomMovie?.year,
                genres = randomMovie?.genres?.let { genreMapper.map(it) }
            )
        }

    fun getFavoriteGenres(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        genreMapper.flowMap(getFavorites(userId)).collect { genres ->
            _favorites.value = genres
        }
    }

    fun getProfileData() = viewModelScope.launch{
        _profile.value = profileUIMapper.map(getProfile())
    }
}