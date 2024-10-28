package com.example.moviecatalog.presentation.entity

data class UserRegisterUIModel(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
