package com.example.moviecatalog.domain.usecase.Validation

import android.view.View
import com.example.moviecatalog.common.Constants.PASSWORD_ERROR
import com.example.moviecatalog.presentation.state.ValidationStatus

class ValidatePasswordUseCase {
    operator fun invoke(password:String):ValidationStatus{
        return if(password.isNotEmpty() && password.length>=6){
            ValidationStatus(
                status = true,
                errorState = View.GONE
            )
        }else{
            ValidationStatus(
                status = false,
                errorMessage = PASSWORD_ERROR,
                errorState = View.VISIBLE
            )
        }
    }
}