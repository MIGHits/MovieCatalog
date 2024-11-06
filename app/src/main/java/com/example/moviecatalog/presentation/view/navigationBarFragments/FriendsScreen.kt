package com.example.moviecatalog.presentation.view.navigationBarFragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.UserFriendsBinding
import com.example.moviecatalog.presentation.view.adapter.FriendListAdapter
import com.example.moviecatalog.presentation.view_model.FriendsScreenViewModel
import com.example.moviecatalog.presentation.view_model.FriendsScreenViewModelFactory
import kotlinx.coroutines.launch

class FriendsScreen : Fragment(R.layout.user_friends) {
    private var binding: UserFriendsBinding? = null
    private lateinit var viewModel: FriendsScreenViewModel
    private var  userId:String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            this,
            FriendsScreenViewModelFactory()
        ).get(FriendsScreenViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userId =  arguments?.getString("userId")
        binding = UserFriendsBinding.bind(view)
        binding?.backButton?.setOnClickListener {
            findNavController().navigate(R.id.action_friendsScreen_to_profileScreen)
        }
        val friendList = binding?.friendRecycler
        friendList?.adapter = FriendListAdapter()
        friendList?.layoutManager = GridLayoutManager(
            requireContext(),
            3,
            GridLayoutManager.VERTICAL,
            false
        )

        getFriends(friendList?.adapter as FriendListAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun getFriends(adapter: FriendListAdapter) {
        lifecycleScope.launch {
            userId?.let { viewModel.getFriends(it).join() }
            viewModel.friends?.collect { friends ->
                adapter.data.addAll(friends)
                adapter.notifyDataSetChanged()
            }
        }
    }
}