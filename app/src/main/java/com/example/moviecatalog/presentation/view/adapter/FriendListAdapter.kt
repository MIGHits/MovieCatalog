package com.example.moviecatalog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FriendListItemBinding
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.view.adapter.MovieCollectionRecyclerAdapter.MovieAdapterViewHolder
import com.squareup.picasso.Picasso

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {
    var data: MutableList<UserShortModelUI> =
        emptyList<UserShortModelUI>().toMutableList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class FriendListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FriendListItemBinding.bind(itemView)
        val avatar = binding.friendAvatar
        val name = binding.friendName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_list_item, parent, false)
        return FriendListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.apply {
            Picasso.get()
                .load(data[position].avatar)
                .fit()
                .centerCrop()
                .into(avatar)
            name.text = data[position].nickName
        }
    }
}