package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.domain.usecase.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.GetFriendsUseCase
import com.example.moviecatalog.presentation.entity.UserShortModelUI
import com.example.moviecatalog.presentation.mappers.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FriendsScreenViewModel(
    private val getFriendsUseCase: GetFriendsUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase,
    private val userMapper: UserMapper
) : ViewModel() {
    var friends: Flow<List<UserShortModelUI>>? = null

    fun getFriends(userId: String) = viewModelScope.launch(Dispatchers.IO) {
        friends = userMapper.friendsFlow(getFriendsUseCase(userId))
    }

    fun deleteFriend(friendId: String, userId: String) = viewModelScope.launch(Dispatchers.IO) {
        deleteFriendUseCase(friendId, userId)
    }
}