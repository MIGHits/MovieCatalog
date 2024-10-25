package com.example.moviecatalog.presentation.entity

import android.view.View

data class LoginCredentials(
   val login:String,
   val loginError:String? = null,
   val loginErrorView:Int = View.GONE,
   val password:String,
   val passwordError:String? = null,
   val passwordErrorView:Int = View.GONE,
   val exceptionError:String? = null
)
