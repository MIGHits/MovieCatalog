package com.example.moviecatalog.data.data.remote.entities

data class ReviewModelDTO(
    val id:String,
    val rating:Int,
    val reviewText:String?,
    val isAnonymous:Boolean,
    val createDateTime:String,
    val author:UserShortModelDTO
)
