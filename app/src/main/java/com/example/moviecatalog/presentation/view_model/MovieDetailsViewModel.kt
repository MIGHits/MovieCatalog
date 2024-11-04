package com.example.moviecatalog.presentation.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviecatalog.domain.usecase.AddReviewUseCase
import com.example.moviecatalog.domain.usecase.DateConverterUseCase
import com.example.moviecatalog.domain.usecase.DeleteReviewUseCase
import com.example.moviecatalog.domain.usecase.EditReviewUseCase
import com.example.moviecatalog.domain.usecase.GetDirectorPosterUseCase
import com.example.moviecatalog.domain.usecase.GetMovieDetailsUseCase
import com.example.moviecatalog.domain.usecase.GetMovieRatingsUseCase
import com.example.moviecatalog.domain.usecase.GetUserProfileDataUseCase
import com.example.moviecatalog.presentation.entity.MovieDetailsModelUI
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.entity.UserReviewUI
import com.example.moviecatalog.presentation.mappers.MovieDetailsMapperUI
import com.example.moviecatalog.presentation.mappers.ProfileUIMapper
import com.example.moviecatalog.presentation.mappers.UserReviewMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
    private val dateConverter: DateConverterUseCase
) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsModelUI> =
        mutableStateOf(MovieDetailsModelUI())
    val movieDetails: State<MovieDetailsModelUI> get() = _movieDetails

    private val _userProfile: MutableState<ProfileModelUI> = mutableStateOf(ProfileModelUI())
    val userProfile: State<ProfileModelUI> get() = _userProfile

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

}