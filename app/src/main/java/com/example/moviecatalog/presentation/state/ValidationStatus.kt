package com.example.moviecatalog.presentation.state

import android.view.View

data class ValidationStatus(
    val status:Boolean,
    val errorMessage:String? = null,
    val errorState:Int = View.GONE
)
