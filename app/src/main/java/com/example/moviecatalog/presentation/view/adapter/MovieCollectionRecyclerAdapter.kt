package com.example.moviecatalog.presentation.view.adapter

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.MovieCollectionItemBinding
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.example.moviecatalog.presentation.view_model.MovieScreenViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.coroutineScope
import java.text.DecimalFormatSymbols
import java.util.Locale

class MovieCollectionRecyclerAdapter() :
    RecyclerView.Adapter<MovieCollectionRecyclerAdapter.MovieAdapterViewHolder>() {
    private val symbols = DecimalFormatSymbols(Locale.getDefault())
    var data: MutableList<MovieElementModelUI> =
        emptyList<MovieElementModelUI>().toMutableList()


    class MovieAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MovieCollectionItemBinding.bind(itemView)
        val moviePoster = binding.moviePoster
        val movieRating = binding.movieRating

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_collection_item, parent, false)
        return MovieAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        setRating(holder.movieRating, data[position].reviews)
        Picasso.get()
            .load(data[position].poster)
            .fit()
            .centerCrop()
            .into(holder.moviePoster)
    }

    fun setRating(text: TextView, rating: Double) {
        when {
            rating in 1.0..<2.3 -> text.setBackgroundResource(R.color.rating1_0)
            rating in 2.3..<3.6 -> text.setBackgroundResource(R.color.rating2_3)
            rating in 3.6..<4.0 -> text.setBackgroundResource(R.color.rating3_6)
            rating in 4.0..<6.0 -> text.setBackgroundResource(R.color.rating4_0)
            rating in 6.0..<7.4 -> text.setBackgroundResource(R.color.rating6_0)
            rating in 7.4..<8.5 -> text.setBackgroundResource(R.color.rating7_4)
            rating in 8.5..<9.9 -> text.setBackgroundResource(R.color.rating8_5)
            rating >= 9.9 -> text.setBackgroundResource(R.color.rating9_9)
        }
        symbols.decimalSeparator = '.'
        val df = java.text.DecimalFormat("#.#", symbols)
        text.text = df.format(rating)
    }

}