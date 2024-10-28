package com.example.moviecatalog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FavoriteMoviesRecyclerItemBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.squareup.picasso.Picasso

class FavoriteMovieAdapter(private val layoutManager: LinearLayoutManager) :
    RecyclerView.Adapter<FavoriteMovieAdapter.MovieAdapterViewHolder>() {
    var data: List<MovieElementModelUI> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MovieAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FavoriteMoviesRecyclerItemBinding.bind(itemView)
        val moviePoster = binding.moviePoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_movies_recycler_item, parent, false)
        return MovieAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {

        val firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        Picasso.get()
            .load(data[position].poster)
            .fit()
            .centerCrop()
            .into(holder.moviePoster)

        if (position == firstCompletelyVisiblePosition) {
            holder.itemView.scaleX = 1.2f
            holder.itemView.scaleY = 1.07f
        } else {
            holder.itemView.scaleX = 1.0f
            holder.itemView.scaleY = 1.0f
        }
    }
}