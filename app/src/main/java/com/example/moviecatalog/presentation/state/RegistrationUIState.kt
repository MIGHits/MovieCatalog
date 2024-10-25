package com.example.moviecatalog.presentation.state

import android.text.InputType
import android.view.View
import com.example.moviecatalog.R

data class RegistrationUIState(
    val loginIconVisibility: Int = View.INVISIBLE,
    val nameIconVisibility: Int = View.INVISIBLE,
    val emailIconVisibility: Int = View.INVISIBLE,
    val passwordCurrentIcon: Int = R.drawable.eye_on,
    val passwordCurrentInputType:Int = InputType.TYPE_TEXT_VARIATION_PASSWORD,
    val passwordConfirmCurrentIcon:Int = R.drawable.eye_on,
    val passwordConfirmCurrentInputType:Int = InputType.TYPE_TEXT_VARIATION_PASSWORD,
    val registrationError:Int = View.GONE
)
