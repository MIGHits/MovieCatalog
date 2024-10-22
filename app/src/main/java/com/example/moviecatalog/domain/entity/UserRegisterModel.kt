package com.example.moviecatalog.domain.entity

data class UserRegisterModel (
    val userName:String,
    val name:String,
    val password:String,
    val email:String,
    val birthDate:String,
    val gender:Int
)
