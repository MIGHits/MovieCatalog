package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.common.Constants.MIN_SWIPE_DISTANCE
import com.example.moviecatalog.databinding.FeedScreenBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.view.MovieDetails.Companion.MOVIE_ID
import com.example.moviecatalog.presentation.view_model.FeedViewModel
import com.example.moviecatalog.presentation.view_model.FeedViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class FeedScreen : Fragment(R.layout.feed_screen) {
    private lateinit var binding: FeedScreenBinding
    private lateinit var movieModel: MovieElementModelUI
    private lateinit var viewModel: FeedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, FeedViewModelFactory())
            .get(FeedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FeedScreenBinding.bind(view)
        binding.imageSwitcher.clipToOutline = true
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val imageSwitcher = binding.imageSwitcher

        imageSwitcher.setOnClickListener {
            bundle.putString(MOVIE_ID, movieModel.id)
            findNavController()
                .navigate(R.id.action_feedScreen_to_movieDetails, bundle)
        }

        subscribeMovieViewModel(viewModel)
        setMovieInfo()
        movieSwipe(imageSwitcher)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun movieSwipe(imageSwitcher: ImageView) {

        imageSwitcher.setOnTouchListener(
            View.OnTouchListener { v, event ->

                resources.displayMetrics
                val cardWidth = imageSwitcher.width
                val cardStart = imageSwitcher.left

                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        var currentX = imageSwitcher.x
                        imageSwitcher.animate()
                            .x(cardStart.toFloat())
                            .setDuration(150)
                            .setListener(
                                object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                                            delay(100)

                                            if (currentX < MIN_SWIPE_DISTANCE) {
                                                setMovieInfo()
                                                currentX = 0f
                                            }
                                        }
                                    }
                                }
                            )
                            .start()
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val newX = event.rawX
                        if (newX - cardWidth < cardStart) {
                            imageSwitcher.animate()
                                .x(
                                    min(cardStart.toFloat(), newX - (cardWidth / 2))
                                )
                                .setDuration(0)
                                .start()
                            if (imageSwitcher.x < MIN_SWIPE_DISTANCE) {

                            } else {

                            }
                        }
                    }
                }
                v.performClick()
                return@OnTouchListener true
            })
    }

    private fun subscribeMovieViewModel(viewModel: FeedViewModel) {
        this.lifecycleScope.launch {
            viewModel.movieModel.collect { movie ->
                movieModel = movie
            }
        }
    }

    private fun setMovieInfo() {
        this.lifecycleScope.launch {
            viewModel.getMovie().join()
            val movieCountryInfo = movieModel.country + "•" + movieModel.year
            val imageView = binding.imageSwitcher
            Picasso.get().load(movieModel.poster).into(imageView)
            binding.apply {
                movieTittle.text = movieModel.name
                movieCountry.text = movieCountryInfo

                for (i in 0..<movieModel.genres?.size!!) {
                    if (i == 3) break else {
                        isGenreFavorite(movieModel.genres?.get(i)?.name, i)
                    }
                }
                imageSwitcher.setImageDrawable(imageView.drawable)
            }
        }
    }

    private fun isGenreFavorite(genre: String?, index: Int) {
        binding.apply {
            val genres = listOf(firstGenre, secondGenre, thirdGenre)
            genres[index].text = genre
            genres[index].setBackgroundResource(R.drawable.genres_card_style)
        }
    }
}