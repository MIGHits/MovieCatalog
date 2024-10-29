package com.example.moviecatalog.presentation.entity

import com.example.moviecatalog.common.Constants.INITIAL_FIELD_STATE
import com.example.moviecatalog.common.Constants.MALE

data class ProfileModelUI(
    val id:String = INITIAL_FIELD_STATE,
    val nickName:String? = INITIAL_FIELD_STATE,
    val email:String = INITIAL_FIELD_STATE,
    val avatarLink:String? = null,
    val name:String = INITIAL_FIELD_STATE,
    val birthDate:String = INITIAL_FIELD_STATE,
    val gender:Int = MALE
)
