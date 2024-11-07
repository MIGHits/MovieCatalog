package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.MIN_SWIPE_DISTANCE
import com.example.moviecatalog.databinding.FeedScreenBinding
import com.example.moviecatalog.presentation.entity.GenreModelUI
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.entity.ProfileModelUI
import com.example.moviecatalog.presentation.view.MovieDetails.Companion.MOVIE_ID
import com.example.moviecatalog.presentation.view_model.FeedViewModel
import com.example.moviecatalog.presentation.view_model.FeedViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.min

class FeedScreen : Fragment(R.layout.feed_screen) {
    private var binding: FeedScreenBinding? = null
    private lateinit var movieModel: MovieElementModelUI
    private lateinit var viewModel: FeedViewModel
    private lateinit var favoriteGenres: List<GenreModelUI>
    private lateinit var userId: ProfileModelUI
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, FeedViewModelFactory())
            .get(FeedViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FeedScreenBinding.bind(view)
        binding?.imageSwitcher?.clipToOutline = true

        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val imageSwitcher = binding?.imageSwitcher
        imageSwitcher?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                bundle.putString(MOVIE_ID, movieModel.id)
                findNavController()
                    .navigate(R.id.action_feedScreen_to_movieDetails, bundle)
                true
            } else {
                false
            }
        }
        subscribeMovieViewModel(viewModel)
        subscribeFavoriteGenres(viewModel)
        subscribeProfileData(viewModel)
        setMovieInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun subscribeMovieViewModel(viewModel: FeedViewModel) {
        this.lifecycleScope.launch {
            viewModel.movieModel.collect { movie ->
                movieModel = movie
            }
        }
    }

    private fun subscribeProfileData(viewModel: FeedViewModel) {
        this.lifecycleScope.launch {
            viewModel.getProfileData().join()
            viewModel.profile.collect { data ->
                userId = data
            }
        }

    }

    private fun subscribeFavoriteGenres(viewModel: FeedViewModel) {
        this.lifecycleScope.launch {
            viewModel.favorites.collect { genres ->
                favoriteGenres = genres
            }
        }
    }

    private fun updateGenresList(userId: String) {
        this.lifecycleScope.launch {
            viewModel.getFavoriteGenres(userId).join()
        }
    }

    private fun setMovieInfo() {
        this.lifecycleScope.launch {
            viewModel.getMovie().join()
            val movieCountryInfo = movieModel.country + "•" + movieModel.year
            val imageView = binding?.imageSwitcher
            Picasso.get().load(movieModel.poster).into(imageView)
            binding?.apply {
                movieTittle.text = movieModel.name
                movieCountry.text = movieCountryInfo

                for (i in 0..<movieModel.genres?.size!!) {
                    if (i == 3) break else {
                        movieModel.genres?.get(i)?.let { isGenreFavorite(it, i, favoriteGenres) }
                    }
                }
                imageSwitcher.setImageDrawable(imageView?.drawable)
            }
        }
    }

    private fun isGenreFavorite(genre: GenreModelUI, index: Int, favorites: List<GenreModelUI>) {
        binding?.apply {
            val genres = listOf(firstGenre, secondGenre, thirdGenre)
            genres[index].text = genre.name
            if (favorites.contains(genre)) {
                genres[index].setBackgroundResource(R.drawable.favorite_genres_card)
            } else {
                genres[index].setBackgroundResource(R.drawable.genres_card_style)
            }
        }
    }
}