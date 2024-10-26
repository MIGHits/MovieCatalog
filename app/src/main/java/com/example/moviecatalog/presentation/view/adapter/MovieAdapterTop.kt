package com.example.moviecatalog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.entity.MovieElementModelUI
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay

class MovieAdapterTop:RecyclerView.Adapter<MovieAdapterTop.MovieAdapterViewHolder>() {
    private val MAX_ITEMS = 5
    var data:List<MovieAdapterElement> = emptyList()
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }
    class MovieAdapterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val moviePoster:ImageView = itemView.findViewById(R.id.currentImage)
        val movieTittle:TextView = itemView.findViewById(R.id.movieLargeTittle)
        val movieGenreFirst:TextView = itemView.findViewById(R.id.first)
        val movieGenreSecond:TextView = itemView.findViewById(R.id.second)
        val movieGenreThird:TextView = itemView.findViewById(R.id.third)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_screen_top_recycler_item,parent,false)
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
            movieGenreFirst.text = data[position].genres?.get(0)?.name
            movieGenreFirst.setBackgroundResource(R.drawable.genres_card_style)
            movieGenreSecond.text = data[position].genres?.get(1)?.name
            movieGenreSecond.setBackgroundResource(R.drawable.genres_card_style)
            movieGenreThird.text = data[position].genres?.get(2)?.name
            movieGenreThird.setBackgroundResource(R.drawable.genres_card_style)
        }
    }
}