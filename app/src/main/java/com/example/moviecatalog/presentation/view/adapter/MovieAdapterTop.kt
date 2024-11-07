package com.example.moviecatalog.presentation.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.MoviesScreenTopRecyclerItemBinding
import com.example.moviecatalog.presentation.view.MovieDetails.Companion.MOVIE_ID
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModel
import com.squareup.picasso.Picasso

class MovieAdapterTop(viewModel: MovieScreenViewModel) :
    RecyclerView.Adapter<MovieAdapterTop.MovieAdapterViewHolder>() {
    private val MAX_ITEMS = 5
    var data: List<MovieAdapterElement> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MovieAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MoviesScreenTopRecyclerItemBinding.bind(itemView)
        val moviePoster = binding.currentImage
        val movieTittle = binding.movieLargeTittle
        val movieGenreFirst = binding.first
        val watchButton = binding.watch
        val movieGenreSecond = binding.second
        val movieGenreThird = binding.third
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_screen_top_recycler_item, parent, false)
        return MovieAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return Math.min(data.size, MAX_ITEMS);
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        Picasso.get()
            .load(data[position].poster)
            .fit()
            .centerCrop()
            .into(holder.moviePoster)
        holder.apply {
            movieTittle.text = data[position].movieTittle
            val genres = listOf(movieGenreFirst, movieGenreSecond, movieGenreThird)

            for (i in genres.indices) {
                genres[i].text = data[position].genres?.get(i)?.name
                if (genres[i].text.isNotEmpty()) {
                    genres[i].setBackgroundResource(R.drawable.genres_card_style)
                }
            }
        }
    }
}