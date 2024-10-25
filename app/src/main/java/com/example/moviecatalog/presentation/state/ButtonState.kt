package com.example.moviecatalog.presentation.state

import com.example.moviecatalog.R

data class ButtonState(
    val buttonState:Boolean = false,
    val buttonStyle:Int = R.drawable.secondary_button_shape,
    val buttonTextStyle:Int =  R.style.buttonTextNotActive
)
