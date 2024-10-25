package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FeedScreenBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.view_model.FeedViewModel
import com.example.moviecatalog.presentation.view_model.FeedViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class FeedScreen:Fragment(R.layout.feed_screen) {
    private var binding:FeedScreenBinding? = null
    private lateinit var movieModel:MovieElementModelUI
    private lateinit var viewModel: FeedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
         viewModel = ViewModelProvider(this,FeedViewModelFactory())
            .get(FeedViewModel::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FeedScreenBinding.bind(view)
        binding?.imageSwitcher?.clipToOutline = true
        super.onViewCreated(view, savedInstanceState)
        subscribeMovieViewModel(viewModel)
    }

    private fun subscribeMovieViewModel(viewModel: FeedViewModel){
        lifecycleScope.launch {
            viewModel.getMovie().join()
            movieModel = viewModel.movieModel.value
            setMovieInfo()
        }
    }

    private fun setMovieInfo(){
        val movieCountryInfo = movieModel.country+"•"+movieModel.year
        val imageView = binding?.imageSwitcher
        Picasso.get().load(movieModel.poster).into(imageView)
        binding?.apply {
            movieTittle.text = movieModel.name
            movieCountry.text = movieCountryInfo

            for (i in 0 ..< movieModel.genres?.size!!){
                if (i==3) break else {
                    isGenreFavorite(movieModel.genres?.get(i)?.name,i)
                }
            }

            if (imageView != null) {
                imageSwitcher.setImageDrawable(imageView.drawable)
            }
        }
    }

    private fun isGenreFavorite(genre:String?,index:Int){
        binding?.apply {
            val genres = listOf(firstGenre,secondGenre,thirdGenre)
            genres[index].text = genre
            genres[index].setBackgroundResource(R.drawable.genres_card_style)
        }
    }
}