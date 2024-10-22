package com.example.moviecatalog.data.data.remote.entities

data class UserRegisterModelDTO (
    val userName:String,
    val name:String,
    val password:String,
    val email:String,
    val birthDate:String,
    val gender:Int
)
