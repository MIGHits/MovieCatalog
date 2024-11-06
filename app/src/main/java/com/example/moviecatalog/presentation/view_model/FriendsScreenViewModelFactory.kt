package com.example.moviecatalog.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.data.mappers.BanedMoviesMapper
import com.example.moviecatalog.data.data.mappers.DbFriendsMapper
import com.example.moviecatalog.data.data.mappers.DbGenresToDomain
import com.example.moviecatalog.data.data.mappers.DbUserMapper
import com.example.moviecatalog.data.data.repository.DatabaseRepositoryImpl
import com.example.moviecatalog.domain.usecase.DeleteFriendUseCase
import com.example.moviecatalog.domain.usecase.GetFriendsUseCase
import com.example.moviecatalog.presentation.mappers.UserMapper

class FriendsScreenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dbRepository = DatabaseRepositoryImpl(
            DbUserMapper(),
            DbFriendsMapper(),
            DbGenresToDomain(),
            BanedMoviesMapper()
        )
        return FriendsScreenViewModel(
            GetFriendsUseCase(
                dbRepository
            ),
            DeleteFriendUseCase(
                dbRepository
            ),
            UserMapper()
        ) as T
    }
}