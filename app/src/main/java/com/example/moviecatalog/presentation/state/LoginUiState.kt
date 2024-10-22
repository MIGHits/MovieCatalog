package com.example.moviecatalog.presentation.state

import android.text.InputType
import android.view.View
import com.example.moviecatalog.R

data class LoginUiState(
    val loginIconVisibility: Int = View.INVISIBLE,
    val passwordCurrentIcon: Int = R.drawable.eye_on,
    val passwordCurrentInputType:Int = InputType.TYPE_TEXT_VARIATION_PASSWORD
)
