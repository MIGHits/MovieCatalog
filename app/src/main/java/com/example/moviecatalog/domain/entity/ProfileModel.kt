package com.example.moviecatalog.domain.entity

data class ProfileModel(
    val id:String,
    val nickName:String?,
    val email:String,
    val avatarLink:String?,
    val name:String,
    val birthDate:String,
    val gender:Int
)
