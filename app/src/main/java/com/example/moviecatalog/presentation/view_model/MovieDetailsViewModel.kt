package com.example.moviecatalog.presentation.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecatalog.domain.entity.Friend
import com.example.moviecatalog.domain.entity.GenreModel
import com.example.moviecatalog.domain.entity.UserShortModel
import com.example.moviecatalog.domain.usecase.AddFavoritegenreUseCase
import com.example.moviecatalog.domain.usecase.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.AddUserDbUseCase
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.DeleteFavoriteGenreUseCase
import com.example.moviecatalog.domain.usecase.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.EditReviewUseCase
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetFavoriteGenresUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.domain.usecase.GetUserDbUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.MovieDetailsModelUI
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.entity.UserReviewUI
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.mappers.GenreMapper
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import com.example.moviecatalog.presentation.mappers.UserMapper
import com.example.moviecatalog.presentation.mappers.UserReviewMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieDetailsViewModel(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val movieDetailsMapperUI: MovieDetailsMapperUI,
    private val getMovieRatings: GetMovieRatingsUseCase,
    private val getPoster: GetDirectorPosterUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val editReviewUseCase: EditReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val reviewMapper: UserReviewMapper,
    private val getProfile: GetUserProfileDataUseCase,
    private val profileMapperUI: ProfileUIMapper,
    private val dateConverter: DateConverterUseCase,
    private val genreMapper: GenreMapper,
    private val userMapper: UserMapper,
    private val addUserDbUseCase: AddUserDbUseCase,
    private val getUserDbUseCase: GetUserDbUseCase,
    private val addFavoritegenreUseCase: AddFavoritegenreUseCase,
    private val getFavoriteGenresUseCase: GetFavoriteGenresUseCase,
    private val deleteFavoriteGenreUseCase: DeleteFavoriteGenreUseCase

) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsModelUI> =
        mutableStateOf(MovieDetailsModelUI())
    val movieDetails: State<MovieDetailsModelUI> get() = _movieDetails

    private val _userProfile: MutableState<ProfileModelUI> = mutableStateOf(ProfileModelUI())
    val userProfile: State<ProfileModelUI> get() = _userProfile

    private val _users: MutableState<List<UserShortModelUI>> = mutableStateOf(emptyList())
    val users: State<List<UserShortModelUI>> get() = _users

    var favorites: Flow<List<GenreModelUI>>? = null
//    private val _favorites: MutableState<List<GenreModelUI>> = mutableStateOf(emptyList())
//    val favorites: State<List<GenreModelUI>> get() = _favorites


    fun getDetails(id: String) = viewModelScope.launch {
        _movieDetails.value =
            movieDetailsMapperUI.map(getMovieDetails(id))
        convertReviewsTime()
        getRatings(
            _movieDetails.value.name.toString(),
            _movieDetails.value.year
        ).join()

        getDirectorPoster(_movieDetails.value.director.toString()).join()
    }

    fun convertReviewsTime() {
        if (_movieDetails.value.reviews?.isNotEmpty() == true) {
            val reviewList = _movieDetails.value.reviews
            reviewList?.forEach { review ->
                review.createDateTime =
                    dateConverter.convertReviewDate(review.createDateTime + "Z")
            }
        }
    }

    fun getRatings(name: String, year: Int) = viewModelScope.launch {
        val ratings = getMovieRatings(name, year)
        _movieDetails.value = _movieDetails.value.copy(
            kinopoiskRating = ratings.ratingKinopoisk,
            imdbRating = ratings.ratingImdb
        )
    }

    fun getUserData() = viewModelScope.launch {
        _userProfile.value = profileMapperUI.map(getProfile())
    }

    fun getDirectorPoster(name: String) = viewModelScope.launch {
        val poster = getPoster(name)
        _movieDetails.value = _movieDetails.value.copy(directorPoster = poster)
    }

    fun addReview(movieId: String, userReview: UserReviewUI) = viewModelScope.launch {
        addReviewUseCase(movieId, reviewMapper.map(userReview))
    }

    fun editReview(movieId: String, reviewId: String, userReview: UserReviewUI) =
        viewModelScope.launch {
            editReviewUseCase(movieId, reviewId, reviewMapper.map(userReview))
        }

    fun deleteReview(movieId: String, reviewId: String) = viewModelScope.launch {
        deleteReviewUseCase(movieId, reviewId)
    }

    fun addUser() = viewModelScope.launch(Dispatchers.IO) {
        addUserDbUseCase(
            UserShortModel(
                _userProfile.value.id,
                _userProfile.value.nickName,
                _userProfile.value.avatarLink
            )
        )
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _users.value = userMapper.dbUsers(getUserDbUseCase())
    }

    fun addFavoriteGenre(genre: GenreModelUI) = viewModelScope.launch(Dispatchers.IO) {
        addFavoritegenreUseCase(genreMapper.reverseMap(genre), _userProfile.value.id)
    }

    fun getFavoriteGenres() = viewModelScope.launch(Dispatchers.IO) {
        favorites = genreMapper.flowMap(getFavoriteGenresUseCase(_userProfile.value.id))
        //_favorites.value = genreMapper.map(getFavoriteGenresUseCase(_userProfile.value.id))

    }

    fun deleteFavoritegenre(genreId: String) = viewModelScope.launch(Dispatchers.IO) {
        deleteFavoriteGenreUseCase(_userProfile.value.id, genreId)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> {
                when (exception.code()) {
                }
            }
        }
    }
}