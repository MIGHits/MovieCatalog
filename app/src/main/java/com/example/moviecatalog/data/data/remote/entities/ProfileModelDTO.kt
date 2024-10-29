package com.example.moviecatalog.data.data.remote.entities

data class ProfileModelDTO(
    val id: String,
    val nickName: String?,
    val email: String,
    val avatarLink: String?,
    val name: String,
    val birthDate: String,
    val gender: Int
)